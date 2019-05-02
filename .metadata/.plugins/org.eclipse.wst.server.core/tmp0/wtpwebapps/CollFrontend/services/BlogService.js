app.factory('BlogService',function($http){

	var blogService={};
	const SERVER_URL="http://localhost:8574/CollMiddleware";

	blogService.getBlogsApproved=function(){
		return $http.get(SERVER_URL+"/blogsApproved");
	}

	blogService.getBlogsPending=function(){
		return $http.get(SERVER_URL+"/blogsPending");
	}

	blogService.approveBlog=function(blogId){

		return $http.get(SERVER_URL+"/approveBlog?blogId="+blogId);

	}

	blogService.rejectBlog=function(blogId){

		return $http.get(SERVER_URL+"/rejectBlog?blogId="+blogId);

	}

	blogService.blogDetails=function(blogId){

		return $http.get(SERVER_URL+"/getBlog?blogId="+blogId);

	}
	blogService.getMyBlogs=function(){

		return $http.get(SERVER_URL+"/getMyBlogs");

	}
	blogService.addComment=function(blogId,blogComment){

		return $http.post(SERVER_URL+"/addBlogComment?blogId="+blogId,blogComment);

	}
	blogService.getAllBlogComments=function(blogId){

		return $http.get(SERVER_URL+"/getBlogComments?blogId="+blogId);

	}
	blogService.deleteBlogComment=function(blogComment){

		return $http.put(SERVER_URL+"/deleteBlogComment",blogComment);
	}
	blogService.likeBlog=function(blogId){

		return $http.get(SERVER_URL+"/likeBlog?blogId="+blogId);

	}
	blogService.dislikeBlog=function(blogId){

		return $http.get(SERVER_URL+"/dislikeBlog?blogId="+blogId);

	}
	blogService.likesForBlog=function(blogId){

		return $http.get(SERVER_URL+"/usersLikedBlog?blogId="+blogId);

	}
	blogService.dislikesForBlog=function(blogId){

		return $http.get(SERVER_URL+"/usersDislikedBlog?blogId="+blogId);

	}

	return blogService;




})