package com.collbackend.daos;

import java.util.List;

import com.collbackend.models.BlogDislikes;
import com.collbackend.models.BlogLikes;

public interface BlogLikeDao {
	
    boolean hasUserLikedBlog(int blogId,String email, String userName);
    boolean hasUserDisLikedBlog(int blogId,String email, String userName);
    List<BlogLikes> getBlogLike(int blogId);
    List<BlogDislikes> getBlogDislike(int blogId);
   
}
