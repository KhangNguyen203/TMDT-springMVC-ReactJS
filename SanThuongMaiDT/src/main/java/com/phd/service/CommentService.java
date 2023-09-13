package com.phd.service;

import java.util.List;
import com.phd.pojo.Comments;

public interface CommentService {

    List<Comments> getComments(int productId);

    Comments addComment(Comments c);
}
