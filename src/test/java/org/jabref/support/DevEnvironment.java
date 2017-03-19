begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.support
package|package
name|org
operator|.
name|jabref
operator|.
name|support
package|;
end_package

begin_comment
comment|/**  * Checks whether we are on a local development environment and not on a CI server.  * This is needed as some remote fetcher tests are blocked by Google when executed on CI servers.  */
end_comment

begin_class
DECL|class|DevEnvironment
specifier|public
class|class
name|DevEnvironment
block|{
DECL|method|isCIServer ()
specifier|public
specifier|static
name|boolean
name|isCIServer
parameter_list|()
block|{
comment|// See http://docs.travis-ci.com/user/environment-variables/#Default-Environment-Variables
comment|// See https://circleci.com/docs/environment-variables
return|return
name|Boolean
operator|.
name|valueOf
argument_list|(
name|System
operator|.
name|getenv
argument_list|(
literal|"CI"
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

