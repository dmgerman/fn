begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_comment
comment|/**  * Class for working with Digital object identifiers (DOIs)  *  * @see https://en.wikipedia.org/wiki/Digital_object_identifier  */
end_comment

begin_class
DECL|class|DOI
specifier|public
class|class
name|DOI
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|DOI
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// DOI resolver
DECL|field|RESOLVER
specifier|public
specifier|static
specifier|final
name|URI
name|RESOLVER
init|=
name|URI
operator|.
name|create
argument_list|(
literal|"http://doi.org"
argument_list|)
decl_stmt|;
comment|// Regex
comment|// (see http://www.doi.org/doi_handbook/2_Numbering.html)
DECL|field|DOI_EXP
specifier|private
specifier|static
specifier|final
name|String
name|DOI_EXP
init|=
literal|""
operator|+
literal|"(?:urn:)?"
comment|// optional urn
operator|+
literal|"(?:doi:)?"
comment|// optional doi
operator|+
literal|"("
comment|// begin group \1
operator|+
literal|"10"
comment|// directory indicator
operator|+
literal|"(?:\\.[0-9]+)+"
comment|// registrant codes
operator|+
literal|"[/:]"
comment|// divider
operator|+
literal|"(?:.+)"
comment|// suffix alphanumeric string
operator|+
literal|")"
decl_stmt|;
comment|// end group \1
DECL|field|HTTP_EXP
specifier|private
specifier|static
specifier|final
name|String
name|HTTP_EXP
init|=
literal|"https?://[^\\s]+?"
operator|+
name|DOI_EXP
decl_stmt|;
comment|// Pattern
DECL|field|DOI_PATT
specifier|private
specifier|static
specifier|final
name|Pattern
name|DOI_PATT
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^(?:https?://[^\\s]+?)?"
operator|+
name|DOI_EXP
operator|+
literal|"$"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
decl_stmt|;
comment|/**      * Creates an Optional<DOI> from various schemes including URL, URN, and plain DOIs.      *      * Useful for suppressing the<c>IllegalArgumentException</c> of the Constructor      * and checking for Optional.isPresent() instead.      *      * @param doi the DOI string      * @return an Optional containing the DOI or an empty Optional      */
DECL|method|build (String doi)
specifier|public
specifier|static
name|Optional
argument_list|<
name|DOI
argument_list|>
name|build
parameter_list|(
name|String
name|doi
parameter_list|)
block|{
try|try
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
operator|new
name|DOI
argument_list|(
name|doi
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
comment|// DOI
DECL|field|doi
specifier|private
specifier|final
name|String
name|doi
decl_stmt|;
comment|/**      * Creates a DOI from various schemes including URL, URN, and plain DOIs.      *      * @param doi the DOI string      * @throws NullPointerException if DOI is null      * @throws IllegalArgumentException if doi does not include a valid DOI      * @return an instance of the DOI class      */
DECL|method|DOI (String doi)
specifier|public
name|DOI
parameter_list|(
name|String
name|doi
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|doi
argument_list|)
expr_stmt|;
comment|// Remove whitespace
name|doi
operator|=
name|doi
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|// HTTP URL decoding
if|if
condition|(
name|doi
operator|.
name|matches
argument_list|(
name|HTTP_EXP
argument_list|)
condition|)
block|{
try|try
block|{
comment|// decodes path segment
name|URI
name|url
init|=
operator|new
name|URI
argument_list|(
name|doi
argument_list|)
decl_stmt|;
name|doi
operator|=
name|url
operator|.
name|getScheme
argument_list|()
operator|+
literal|"://"
operator|+
name|url
operator|.
name|getHost
argument_list|()
operator|+
name|url
operator|.
name|getPath
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|doi
operator|+
literal|" is not a valid HTTP DOI."
argument_list|)
throw|;
block|}
block|}
comment|// Extract DOI
name|Matcher
name|matcher
init|=
name|DOI_PATT
operator|.
name|matcher
argument_list|(
name|doi
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// match only group \1
name|this
operator|.
name|doi
operator|=
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|doi
operator|+
literal|" is not a valid DOI."
argument_list|)
throw|;
block|}
block|}
comment|/**      * Return the plain DOI      *      * @return the plain DOI value.      */
DECL|method|getDOI ()
specifier|public
name|String
name|getDOI
parameter_list|()
block|{
return|return
name|doi
return|;
block|}
comment|/**      * Return a URI presentation for the DOI      *      * @return an encoded URI representation of the DOI      */
DECL|method|getURI ()
specifier|public
name|URI
name|getURI
parameter_list|()
block|{
try|try
block|{
name|URI
name|uri
init|=
operator|new
name|URI
argument_list|(
name|RESOLVER
operator|.
name|getScheme
argument_list|()
argument_list|,
name|RESOLVER
operator|.
name|getHost
argument_list|()
argument_list|,
literal|"/"
operator|+
name|doi
argument_list|,
literal|null
argument_list|)
decl_stmt|;
return|return
name|uri
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
comment|// should never happen
name|LOGGER
operator|.
name|error
argument_list|(
name|doi
operator|+
literal|" could not be encoded as URI."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Return an ASCII URL presentation for the DOI      *      * @return an encoded URL representation of the DOI      */
DECL|method|getURLAsASCIIString ()
specifier|public
name|String
name|getURLAsASCIIString
parameter_list|()
block|{
return|return
name|getURI
argument_list|()
operator|.
name|toASCIIString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

