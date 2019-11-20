package com.github.bugscatcher;

import com.github.bugscatcher.dto.CommentsDTO;
import com.github.bugscatcher.dto.PostDTO;
import com.github.bugscatcher.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.github.bugscatcher.APIHelper.*;
import static com.github.bugscatcher.TestUtil.getMessageForNonExistentProperty;
import static com.github.bugscatcher.TestUtil.getMessageForNonExistentUser;

public class TestsNegative extends Abstract {
    @Test
    public void checkEmailsFormat_nonExistentUser() {
        UserDTO user = searchUser("non-existent");
        Assert.assertNull(user);
    }

    @Test
    public void checkEmailsFormat_noPosts() {
//        Here I'm using ready-made data from db.json. This can be replaced by creating a user, post and comments through requests.
        String property = "username.noposts";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        UserDTO user = searchUser(username);
        Assert.assertNotNull(getMessageForNonExistentUser(username), user);

        PostDTO[] posts = getPosts(user.getId());
        Assert.assertEquals(0, posts.length);
    }

    @Test
    public void checkEmailsFormat_noComments() {
//        Here I'm using ready-made data from db.json. This can be replaced by creating a user, post and comments through requests.
        String property = "username.nocomments";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        UserDTO user = searchUser(username);
        Assert.assertNotNull(getMessageForNonExistentUser(username), user);

        PostDTO[] posts = getPosts(user.getId());
        Arrays.stream(posts)
                .forEach(post -> {
                    CommentsDTO[] comments = getComments(post.getId());
                    Assert.assertEquals(0, comments.length);
                });
    }

    @Test
    public void checkEmailsFormat_incorrectEmail() {
//        Here I'm using ready-made data from db.json. This can be replaced by creating a user, post and comments through requests.
        String property = "username.negative";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        UserDTO user = searchUser(username);
        Assert.assertNotNull(getMessageForNonExistentUser(username), user);

        PostDTO[] posts = getPosts(user.getId());
        Arrays.stream(posts)
                .forEach(post -> {
                    CommentsDTO[] comments = getComments(post.getId());
                    Arrays.stream(comments)
                            .forEach(comment -> {
//                                This is a place to think, i.e. we have an invalid email, the test should pass or not?
                                boolean isValid = TestUtil.isValid(comment.getEmail());
                                Assert.assertFalse(isValid);
                            });
                });
    }
}
