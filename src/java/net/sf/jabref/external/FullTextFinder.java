begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * This interface is used for classes that try to resolve a full-text PDF url from an article  * web page. Implementing classes should specialize on specific article sites.  *  */
end_comment

begin_interface
DECL|interface|FullTextFinder
specifier|public
interface|interface
name|FullTextFinder
block|{
comment|/**      * Report whether this FullTextFinder works for the site providing the given URL.      *      * @param url The url to check.      * @return true if the site is supported, false otherwise. If the site might be supported,      *   it is best to return true.      */
DECL|method|supportsSite (URL url)
specifier|public
name|boolean
name|supportsSite
parameter_list|(
name|URL
name|url
parameter_list|)
function_decl|;
comment|/**      * Take the source HTML for an article page, and try to find the URL to the      * full text for this article.      *      * @param url The URL to the article's web page.      * @return The fulltext PDF URL, if found, or null if not found.      * @throws java.io.IOException      */
DECL|method|findFullTextURL (URL url)
specifier|public
name|URL
name|findFullTextURL
parameter_list|(
name|URL
name|url
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

