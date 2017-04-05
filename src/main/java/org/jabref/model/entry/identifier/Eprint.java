begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.identifier
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
package|;
end_package

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

begin_comment
comment|/**  * Class for working with Eprint identifiers  *  * @see https://arxiv.org/help/arxiv_identifier  * @see https://arxiv.org/hypertex/bibstyles/  */
end_comment

begin_class
DECL|class|Eprint
specifier|public
class|class
name|Eprint
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
name|Eprint
operator|.
name|class
argument_list|)
decl_stmt|;
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
literal|"http://arxiv.org"
argument_list|)
decl_stmt|;
comment|// DOI
DECL|field|eprint
specifier|private
specifier|final
name|String
name|eprint
decl_stmt|;
comment|// Regex
comment|// (see https://arxiv.org/help/arxiv_identifier)
DECL|field|EPRINT_EXP
specifier|private
specifier|static
specifier|final
name|String
name|EPRINT_EXP
init|=
literal|""
operator|+
literal|"(?:arXiv:)?"
comment|// optional prefix
operator|+
literal|"("
comment|// begin group \1
operator|+
literal|"\\d{4}"
comment|// YYMM
operator|+
literal|"\\."
comment|// divider
operator|+
literal|"\\d{4,5}"
comment|// number
operator|+
literal|"(v\\d+)?"
comment|// optional version
operator|+
literal|"|"
comment|// old id
operator|+
literal|".+"
comment|// archive
operator|+
literal|"(\\.\\w{2})?"
comment|// optional subject class
operator|+
literal|"\\/"
comment|// divider
operator|+
literal|"\\d{7}"
comment|// number
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
name|EPRINT_EXP
decl_stmt|;
comment|// Pattern
DECL|field|EXACT_EPRINT_PATT
specifier|private
specifier|static
specifier|final
name|Pattern
name|EXACT_EPRINT_PATT
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^(?:https?://[^\\s]+?)?"
operator|+
name|EPRINT_EXP
operator|+
literal|"$"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
decl_stmt|;
comment|/**      * Creates a Eprint from various schemes including URL.      *      * @param eprint the Eprint identifier string      * @throws NullPointerException if eprint is null      * @throws IllegalArgumentException if eprint does not include a valid Eprint identifier      * @return an instance of the Eprint class      */
DECL|method|Eprint (String eprint)
specifier|public
name|Eprint
parameter_list|(
name|String
name|eprint
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|eprint
argument_list|)
expr_stmt|;
comment|// Remove whitespace
name|String
name|trimmedId
init|=
name|eprint
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// HTTP URL decoding
if|if
condition|(
name|eprint
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
name|trimmedId
argument_list|)
decl_stmt|;
name|trimmedId
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
name|eprint
operator|+
literal|" is not a valid HTTP Eprint identifier."
argument_list|)
throw|;
block|}
block|}
comment|// Extract DOI
name|Matcher
name|matcher
init|=
name|EXACT_EPRINT_PATT
operator|.
name|matcher
argument_list|(
name|trimmedId
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
name|eprint
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
name|trimmedId
operator|+
literal|" is not a valid Eprint identifier."
argument_list|)
throw|;
block|}
block|}
comment|/**      * Creates an Optional<Eprint> from various schemes including URL.      *      * Useful for suppressing the<c>IllegalArgumentException</c> of the Constructor      * and checking for Optional.isPresent() instead.      *      * @param eprint the Eprint string      * @return an Optional containing the Eprint or an empty Optional      */
DECL|method|build (String eprint)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Eprint
argument_list|>
name|build
parameter_list|(
name|String
name|eprint
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
name|Eprint
argument_list|(
name|eprint
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
decl||
name|NullPointerException
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
comment|/**      * Return a URI presentation for the Eprint identifier      *      * @return an encoded URI representation of the Eprint identifier      */
DECL|method|getURI ()
specifier|public
name|Optional
argument_list|<
name|URI
argument_list|>
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
literal|"/abs/"
operator|+
name|eprint
argument_list|,
literal|null
argument_list|)
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|uri
argument_list|)
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
name|eprint
operator|+
literal|" could not be encoded as URI."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
comment|/**      * Return an ASCII URL presentation for the Eprint identifier      *      * @return an encoded URL representation of the Eprint identifier      */
DECL|method|getURIAsASCIIString ()
specifier|public
name|String
name|getURIAsASCIIString
parameter_list|()
block|{
return|return
name|getURI
argument_list|()
operator|.
name|map
argument_list|(
name|URI
operator|::
name|toASCIIString
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
return|;
block|}
comment|/**      * Return the plain Eprint identifier      *      * @return the plain Eprint value.      */
DECL|method|getEprint ()
specifier|public
name|String
name|getEprint
parameter_list|()
block|{
return|return
name|eprint
return|;
block|}
block|}
end_class

end_unit
