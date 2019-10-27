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
//        ... but this does not take into account the fact that we can store several usernames.
//        In this case, we need to read the properties something like `properties.getProperty(property).split(",")`
//        and check user's post in loop.
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        UserDTO user = searchUser(username);
        Assert.assertNotNull(getMessageForNonExistentUser(username), user);

        PostDTO[] posts = getResource(EndPoints.POSTS + user.getId(), PostDTO[].class);
        if (posts.length == 0) {
            LOG.warn("No posts for userId=" + user.getId());
        }

        Arrays.stream(posts)
                .forEach(post -> {
                    CommentsDTO[] comments = getResource(EndPoints.COMMENTS + post.getId(), CommentsDTO[].class);
                    if (comments.length == 0) {
                        LOG.warn("No comments for postId=" + post.getId());
                    }
                    Arrays.stream(comments)
                            .forEach(comment -> {
                                boolean isValid = TestUtil.isValid(comment.getEmail());
                                Assert.assertTrue(getMessageForEmailInvalidFormat(comment.getEmail()), isValid);
                            });
                });
    }
}
