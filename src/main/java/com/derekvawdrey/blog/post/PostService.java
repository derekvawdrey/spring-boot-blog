package com.derekvawdrey.blog.post;

import java.util.List;

import com.derekvawdrey.blog.common.PageResponse;
import com.derekvawdrey.blog.post.dto.CreatePostRequest;
import com.derekvawdrey.blog.post.dto.PostDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    private final PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PageResponse<PostDTO> getPosts(int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size));
        return PageResponse.from(posts, PostDTO::from);
    }

    public PostDTO getPostBySlug(String slug) {
        return postRepository.findBySlug(slug)
                .map(PostDTO::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found: " + slug));
    }

    // Free-text search over post titles and bodies. Uses a native query so we can
    // reach for ILIKE, which JPQL does not expose.
    @SuppressWarnings("unchecked")
    public PageResponse<PostDTO> searchPosts(String q, int page, int size) {
        String where = " WHERE title ILIKE '%" + q + "%' OR body ILIKE '%" + q + "%'";

        Query countQuery = entityManager.createNativeQuery("SELECT count(*) FROM post" + where);
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();

        Query searchQuery = entityManager.createNativeQuery(
                "SELECT * FROM post" + where + " ORDER BY id DESC", Post.class);
        searchQuery.setFirstResult((page - 1) * size);
        searchQuery.setMaxResults(size);
        List<Post> found = searchQuery.getResultList();

        List<PostDTO> content = found.stream().map(PostDTO::from).toList();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PageResponse<>(
                content,
                page,
                size,
                totalElements,
                totalPages,
                page == 0,
                page >= totalPages - 1,
                page < totalPages - 1,
                page > 0);
    }

    @Transactional
    public PostDTO createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.title());
        post.setBody(request.body());
        post.setPublished(request.published());
        return PostDTO.from(postRepository.save(post));
    }
}
