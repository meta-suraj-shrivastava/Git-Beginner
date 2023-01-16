package com.writerHub.practice.Exception;

public class AuthenticationExceptions {
    public static class AuthorNotFound extends Exception{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String message;
        public AuthorNotFound(String message){
            this.message = message;
        }
    }

    public static class AuthorExists extends Exception{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String message;
        public AuthorExists(String message){
            this.message = message;
        }
    }

    public static class AuthorizationHeaderMissing extends Exception{
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String message;
        public AuthorizationHeaderMissing(String message){
            this.message = message;
        }
    }
}
