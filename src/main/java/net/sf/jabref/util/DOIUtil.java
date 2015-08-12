begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
operator|.
name|UndoableFieldChange
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

begin_class
DECL|class|DOIUtil
specifier|public
class|class
name|DOIUtil
block|{
comment|// Base URL
DECL|field|DOI_LOOKUP_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|DOI_LOOKUP_PREFIX
init|=
literal|"http://dx.doi.org/"
decl_stmt|;
comment|// DOI-regexp provided by http://stackoverflow.com/a/10324802/873282
comment|// Some DOI's are not caught by the regexp in the above link, i.e. 10.1002/(SICI)1522-2594(199911)42:5<952::AID-MRM16>3.0.CO;2-S
comment|// Removed<> from non-permitted characters
DECL|field|REGEXP_PLAINDOI
specifier|private
specifier|static
specifier|final
name|String
name|REGEXP_PLAINDOI
init|=
literal|"\\b(10[.][0-9]{4,}(?:[.][0-9]+)*/(?:(?![\"&\\'])\\S)+)\\b"
decl_stmt|;
DECL|field|PATTERN_PLAINDOI
specifier|private
specifier|static
specifier|final
name|Pattern
name|PATTERN_PLAINDOI
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|REGEXP_PLAINDOI
argument_list|)
decl_stmt|;
DECL|field|REGEXP_DOI_WITH_HTTP_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|REGEXP_DOI_WITH_HTTP_PREFIX
init|=
literal|"http[s]?://[^\\s]*?"
operator|+
name|REGEXP_PLAINDOI
decl_stmt|;
comment|/**      * Check if the String matches a DOI (with http://...)      */
DECL|method|checkForDOIwithHTTPprefix (String check)
specifier|public
specifier|static
name|boolean
name|checkForDOIwithHTTPprefix
parameter_list|(
name|String
name|check
parameter_list|)
block|{
return|return
name|check
operator|!=
literal|null
operator|&&
name|check
operator|.
name|matches
argument_list|(
literal|".*"
operator|+
name|REGEXP_DOI_WITH_HTTP_PREFIX
operator|+
literal|".*"
argument_list|)
return|;
block|}
comment|/**      *      * @param check - string to check      * @return true if "check" contains a DOI      */
DECL|method|checkForPlainDOI (String check)
specifier|public
specifier|static
name|boolean
name|checkForPlainDOI
parameter_list|(
name|String
name|check
parameter_list|)
block|{
return|return
name|check
operator|!=
literal|null
operator|&&
name|check
operator|.
name|matches
argument_list|(
literal|".*"
operator|+
name|REGEXP_PLAINDOI
operator|+
literal|".*"
argument_list|)
return|;
block|}
comment|/**      * Remove the http://... from DOI      *      * @param doi - may not be null      * @return first DOI in the given String (without http://... prefix). If no DOI exists, the complete string is returned      */
DECL|method|getDOI (String doi)
specifier|public
specifier|static
name|String
name|getDOI
parameter_list|(
name|String
name|doi
parameter_list|)
block|{
name|Matcher
name|matcher
init|=
name|PATTERN_PLAINDOI
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
return|return
name|matcher
operator|.
name|group
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|doi
return|;
block|}
block|}
DECL|method|removeDOIfromBibtexEntryField (BibtexEntry bes, String fieldName, NamedCompound ce)
specifier|public
specifier|static
name|void
name|removeDOIfromBibtexEntryField
parameter_list|(
name|BibtexEntry
name|bes
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
name|String
name|origValue
init|=
name|bes
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|origValue
decl_stmt|;
name|value
operator|=
name|value
operator|.
name|replaceAll
argument_list|(
name|REGEXP_DOI_WITH_HTTP_PREFIX
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|value
operator|=
name|value
operator|.
name|replaceAll
argument_list|(
name|REGEXP_PLAINDOI
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|value
operator|=
name|value
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|value
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|origValue
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|bes
argument_list|,
name|fieldName
argument_list|,
name|origValue
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|bes
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

