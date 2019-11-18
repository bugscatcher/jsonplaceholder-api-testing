package com.github.bugscatcher;

import com.github.bugscatcher.dto.CommentsDTO;
import com.github.bugscatcher.dto.PostDTO;
import com.github.bugscatcher.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.github.bugscatcher.TestUtil.*;

public class Tests extends Abstract {
    @Test
    public void checkEmailsFormat() {
        String property = "username";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        UserDTO user = searchUser(username);
        Assert.assertNotNull(getMessageForNonExistentUser(username), user);

        PostDTO[] posts = getPosts(user.getId());
        Assert.assertNotEquals(0, posts.length);

        Arrays.stream(posts)
                .forEach(post -> {
                    CommentsDTO[] comments = getComments(post.getId());
                    Arrays.stream(comments)
                            .forEach(comment -> {
                                boolean isValid = TestUtil.isValid(comment.getEmail());
                                Assert.assertTrue(getMessageForEmailInvalidFormat(comment.getEmail()), isValid);
                            });
                });
    }

    @Test
    public void checkEmailsFormat_negative() {
//        Here, I'm using ready-made data from db.json. This can be replaced by creating a user, post and comment through requests.
        String property = "username.negative";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        UserDTO user = searchUser(username);
        PostDTO[] posts = getPosts(user.getId());
        Assert.assertNotEquals(0, posts.length);

        Arrays.stream(posts)
                .forEach(post -> {
                    CommentsDTO[] comments = getComments(post.getId());
                    Arrays.stream(comments)
                            .forEach(comment -> {
                                boolean isValid = TestUtil.isValid(comment.getEmail());
                                Assert.assertTrue(getMessageForEmailInvalidFormat(comment.getEmail()), isValid);
                            });
                });
    }

    private PostDTO[] getPosts(int userID) {
        PostDTO[] posts = getResource(EndPoints.POSTS + userID, PostDTO[].class);
        if (posts.length == 0) {
            LOG.warn("No posts for userId=" + userID);
        }
        return posts;
    }

    private CommentsDTO[] getComments(int postID) {
        CommentsDTO[] comments = getResource(EndPoints.COMMENTS + postID, CommentsDTO[].class);
        if (comments.length == 0) {
            LOG.warn("No comments for postId=" + postID);
        }
        return comments;
    }
}
