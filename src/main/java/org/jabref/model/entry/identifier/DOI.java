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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Class for working with Digital object identifiers (DOIs) and Short DOIs  *  * @see https://en.wikipedia.org/wiki/Digital_object_identifier  * @see http://shortdoi.org  */
end_comment

begin_class
DECL|class|DOI
specifier|public
class|class
name|DOI
implements|implements
name|Identifier
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DOI
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// DOI/Short DOI resolver
DECL|field|RESOLVER
specifier|private
specifier|static
specifier|final
name|URI
name|RESOLVER
init|=
name|URI
operator|.
name|create
argument_list|(
literal|"https://doi.org"
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
literal|"[/:%]"
comment|// divider
operator|+
literal|"(?:.+)"
comment|// suffix alphanumeric string
operator|+
literal|")"
decl_stmt|;
comment|// end group \1
DECL|field|FIND_DOI_EXP
specifier|private
specifier|static
specifier|final
name|String
name|FIND_DOI_EXP
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
literal|"(?:[^\\s]+)"
comment|// suffix alphanumeric without space
operator|+
literal|")"
decl_stmt|;
comment|// end group \1
comment|// Regex (Short DOI)
DECL|field|SHORT_DOI_EXP
specifier|private
specifier|static
specifier|final
name|String
name|SHORT_DOI_EXP
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
literal|"[/:%]"
comment|// divider
operator|+
literal|"[a-zA-Z0-9]+"
operator|+
literal|")"
decl_stmt|;
comment|// end group \1
DECL|field|FIND_SHORT_DOI_EXP
specifier|private
specifier|static
specifier|final
name|String
name|FIND_SHORT_DOI_EXP
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
literal|"[/:]"
comment|// divider
operator|+
literal|"[a-zA-Z0-9]+"
operator|+
literal|"(?:[^\\s]+)"
comment|// suffix alphanumeric without space
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
DECL|field|SHORT_DOI_HTTP_EXP
specifier|private
specifier|static
specifier|final
name|String
name|SHORT_DOI_HTTP_EXP
init|=
literal|"https?://[^\\s]+?"
operator|+
name|SHORT_DOI_EXP
decl_stmt|;
comment|// Pattern
DECL|field|EXACT_DOI_PATT
specifier|private
specifier|static
specifier|final
name|Pattern
name|EXACT_DOI_PATT
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
literal|"(?:https?://[^\\s]+?)?"
operator|+
name|FIND_DOI_EXP
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
decl_stmt|;
comment|// Pattern (short DOI)
DECL|field|EXACT_SHORT_DOI_PATT
specifier|private
specifier|static
specifier|final
name|Pattern
name|EXACT_SHORT_DOI_PATT
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^(?:https?://[^\\s]+?)?"
operator|+
name|SHORT_DOI_EXP
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
decl_stmt|;
DECL|field|SHORT_DOI_PATT
specifier|private
specifier|static
specifier|final
name|Pattern
name|SHORT_DOI_PATT
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?:https?://[^\\s]+?)?"
operator|+
name|FIND_SHORT_DOI_EXP
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
decl_stmt|;
comment|// DOI
DECL|field|doi
specifier|private
specifier|final
name|String
name|doi
decl_stmt|;
comment|// Short DOI
DECL|field|isShortDoi
specifier|private
name|boolean
name|isShortDoi
decl_stmt|;
comment|/**      * Creates a DOI from various schemes including URL, URN, and plain DOIs/Short DOIs.      *      * @param doi the DOI/Short DOI string      * @throws NullPointerException     if DOI/Short DOI is null      * @throws IllegalArgumentException if doi does not include a valid DOI/Short DOI      */
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
name|String
name|trimmedDoi
init|=
name|doi
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// HTTP URL decoding
if|if
condition|(
name|doi
operator|.
name|matches
argument_list|(
name|HTTP_EXP
argument_list|)
operator|||
name|doi
operator|.
name|matches
argument_list|(
name|SHORT_DOI_HTTP_EXP
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
name|trimmedDoi
argument_list|)
decl_stmt|;
name|trimmedDoi
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
literal|" is not a valid HTTP DOI/Short DOI."
argument_list|)
throw|;
block|}
block|}
comment|// Extract DOI/Short DOI
name|Matcher
name|matcher
init|=
name|EXACT_DOI_PATT
operator|.
name|matcher
argument_list|(
name|trimmedDoi
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
comment|// Short DOI
name|Matcher
name|shortDoiMatcher
init|=
name|EXACT_SHORT_DOI_PATT
operator|.
name|matcher
argument_list|(
name|trimmedDoi
argument_list|)
decl_stmt|;
if|if
condition|(
name|shortDoiMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|this
operator|.
name|doi
operator|=
name|shortDoiMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|isShortDoi
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|trimmedDoi
operator|+
literal|" is not a valid DOI/Short DOI."
argument_list|)
throw|;
block|}
block|}
block|}
comment|/**      * Creates an Optional<DOI> from various schemes including URL, URN, and plain DOIs.      *      * Useful for suppressing the<c>IllegalArgumentException</c> of the Constructor and checking for      * Optional.isPresent() instead.      *      * @param doi the DOI/Short DOI string      * @return an Optional containing the DOI or an empty Optional      */
DECL|method|parse (String doi)
specifier|public
specifier|static
name|Optional
argument_list|<
name|DOI
argument_list|>
name|parse
parameter_list|(
name|String
name|doi
parameter_list|)
block|{
try|try
block|{
name|String
name|cleanedDOI
init|=
name|doi
operator|.
name|trim
argument_list|()
decl_stmt|;
name|cleanedDOI
operator|=
name|doi
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|DOI
argument_list|(
name|cleanedDOI
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
comment|/**      * Determines whether a DOI/Short DOI is valid or not      *      * @param doi the DOI/Short DOI string      * @return true if DOI is valid, false otherwise      */
DECL|method|isValid (String doi)
specifier|public
specifier|static
name|boolean
name|isValid
parameter_list|(
name|String
name|doi
parameter_list|)
block|{
return|return
name|parse
argument_list|(
name|doi
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
comment|/**      * Tries to find a DOI/Short DOI inside the given text.      *      * @param text the Text which might contain a DOI/Short DOI      * @return an Optional containing the DOI or an empty Optional      */
DECL|method|findInText (String text)
specifier|public
specifier|static
name|Optional
argument_list|<
name|DOI
argument_list|>
name|findInText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|result
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
name|Matcher
name|matcher
init|=
name|DOI_PATT
operator|.
name|matcher
argument_list|(
name|text
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
name|result
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|DOI
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|matcher
operator|=
name|SHORT_DOI_PATT
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
expr_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|DOI
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"DOI{"
operator|+
literal|"doi='"
operator|+
name|doi
operator|+
literal|'\''
operator|+
literal|'}'
return|;
block|}
comment|/**      * Return the plain DOI/Short DOI      *      * @return the plain DOI/Short DOI value.      */
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
comment|/**      * Determines whether DOI is short DOI or not      *      * @return true if DOI is short DOI, false otherwise      */
DECL|method|isShortDoi ()
specifier|public
name|boolean
name|isShortDoi
parameter_list|()
block|{
return|return
name|isShortDoi
return|;
block|}
comment|/**      * Return a URI presentation for the DOI/Short DOI      *      * @return an encoded URI representation of the DOI/Short DOI      */
annotation|@
name|Override
DECL|method|getExternalURI ()
specifier|public
name|Optional
argument_list|<
name|URI
argument_list|>
name|getExternalURI
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
name|doi
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
comment|/**      * Return an ASCII URL presentation for the DOI/Short DOI      *      * @return an encoded URL representation of the DOI/Short DOI      */
DECL|method|getURIAsASCIIString ()
specifier|public
name|String
name|getURIAsASCIIString
parameter_list|()
block|{
return|return
name|getExternalURI
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
annotation|@
name|Override
DECL|method|getDefaultField ()
specifier|public
name|Field
name|getDefaultField
parameter_list|()
block|{
return|return
name|StandardField
operator|.
name|DOI
return|;
block|}
annotation|@
name|Override
DECL|method|getNormalized ()
specifier|public
name|String
name|getNormalized
parameter_list|()
block|{
return|return
name|doi
return|;
block|}
block|}
end_class

end_unit

