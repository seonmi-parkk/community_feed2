package org.comunity.fake;

import org.comunity.post.application.CommentService;
import org.comunity.post.application.PostService;
import org.comunity.post.application.interfaces.CommentRepository;
import org.comunity.post.application.interfaces.LikeRepository;
import org.comunity.post.application.interfaces.PostRepository;
import org.comunity.post.repository.FakeCommentRepository;
import org.comunity.post.repository.FakeLikeRepository;
import org.comunity.post.repository.FakePostRepository;
import org.comunity.user.application.UserRelationService;
import org.comunity.user.application.UserService;
import org.comunity.user.application.interfaces.UserRelationRepository;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.repository.FakeUserRelationRepository;
import org.comunity.user.repository.FakeUserRepository;

public class FakeObjectFactory {

    // 매번 테스트를 만들때마다 주입받고 생성하고 만들려면 테스트가 복잡해지고
    // 서비스에서 들어가는 인자가 변경될 경우 모두 바꿔줘야하는 문제 발생
    // -> fakeObjectFactory를 싱글톤으로 만들어서 문제 해결

    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();
    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final CommentRepository fakeCommentRepository = new FakeCommentRepository();
    private static final LikeRepository fakeLikeRepositroy = new FakeLikeRepository();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationService = new UserRelationService(userService, fakeUserRelationRepository);
    private static final PostService postService = new PostService(userService, fakePostRepository, fakeLikeRepositroy);
    private static final CommentService commentService = new CommentService(userService, postService, fakeCommentRepository, fakeLikeRepositroy);


    private FakeObjectFactory() {}

    public static UserService getUserService() {
        return userService;
    }
    public static UserRelationService getUserRelationService() {
        return userRelationService;
    }
    public static PostService getPostService() {
        return postService;
    }
    public static CommentService getCommentService() {
        return commentService;
    }


}
