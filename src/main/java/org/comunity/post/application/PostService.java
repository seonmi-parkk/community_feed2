package org.comunity.post.application;

import org.comunity.post.application.dto.CreatePostRequestDto;
import org.comunity.post.application.dto.LikeRequestDto;
import org.comunity.post.application.dto.UpdatePostRequestDto;
import org.comunity.post.application.interfaces.LikeRepository;
import org.comunity.post.application.interfaces.PostRepository;
import org.comunity.post.domain.Post;
import org.comunity.user.application.UserService;
import org.comunity.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;


    public PostService(UserService userService, PostRepository postRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public Post getPost(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(CreatePostRequestDto dto) {
        User author = userService.getUser(dto.userId());
        Post post = Post.createPost(null, author, dto.content(), dto.state());
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, UpdatePostRequestDto dto) {
        // post테이블에서 해당 포스트 조회 -> 유저 조회 -> 포스트 조회 1번더 -> update
        // 왜 한번더 포스트를 조회하는지?
            // 영속성컨텍스트에 포스트엔티티가 등록되어있지 않기때문
            // getPost()에서 쿼리를 실행하고 반환된 postEntity를 Entity로 변환하여 받음.
            // 반환된 postEntity는 사용되지 않음 -> 반환된 postEntity를 그대로 사용해야 영속성 컨텍스트에 등록이 됨.
            // 현재 우리는 로직을 위해 post 객체를 받아서 사용중. -> 한번더 조회를 한 후 영속성컨텍스트에 등록됨.
        // 이런 상황에서 불필요한 조회 없애는 방법 2가지
            // 1. repositoryImpl, service수정
            // 2. update문을 jpql 쿼리문으로 작성
        // 현재 상황에서는 1번 방법은 수정할게 많아져서 2번으로 진행
        // UserReletaionRepository도 같은 문제가 발생할것임. ⇒ 예도 수정하기!

        Post post = getPost(postId);
        User user = userService.getUser(dto.userId());

        post.updateContent(user, dto.content(), dto.state());
        return postRepository.save(post);
    }

    public void likePost(LikeRequestDto dto) {
        Post post = getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(post, user)) {
            return;
        }

        post.like(user);
        likeRepository.like(post, user);
    }

    public void unlikePost(LikeRequestDto dto) {
        Post post = getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(post, user)) {
            post.unlike();
            likeRepository.unlike(post, user);
        }
    }


}
