begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|Set
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
name|logic
operator|.
name|util
operator|.
name|OS
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
name|FieldName
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * This class provides the reformatting needed when reading BibTeX fields formatted  * in JabRef style. The reformatting must undo all formatting done by JabRef when  * writing the same fields.  */
end_comment

begin_class
DECL|class|FieldContentParser
specifier|public
class|class
name|FieldContentParser
block|{
DECL|field|multiLineFields
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|multiLineFields
decl_stmt|;
comment|// 's' matches a space, tab, new line, carriage return.
DECL|field|WHITESPACE
specifier|private
specifier|static
specifier|final
name|Pattern
name|WHITESPACE
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\s+"
argument_list|)
decl_stmt|;
DECL|method|FieldContentParser (FieldContentParserPreferences prefs)
specifier|public
name|FieldContentParser
parameter_list|(
name|FieldContentParserPreferences
name|prefs
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|multiLineFields
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
expr_stmt|;
comment|// the following two are also coded in org.jabref.logic.bibtex.LatexFieldFormatter.format(String, String)
name|multiLineFields
operator|.
name|add
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
expr_stmt|;
name|multiLineFields
operator|.
name|add
argument_list|(
name|FieldName
operator|.
name|REVIEW
argument_list|)
expr_stmt|;
comment|// the file field should not be formatted, therefore we treat it as a multi line field
name|multiLineFields
operator|.
name|addAll
argument_list|(
name|prefs
operator|.
name|getNonWrappableFields
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Performs the reformatting      *      * @param fieldContent the content to format      * @param bibtexField the name of the bibtex field      * @return the formatted field content.      */
DECL|method|format (String fieldContent, String bibtexField)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldContent
parameter_list|,
name|String
name|bibtexField
parameter_list|)
block|{
if|if
condition|(
name|multiLineFields
operator|.
name|contains
argument_list|(
name|bibtexField
argument_list|)
condition|)
block|{
comment|// Unify line breaks
return|return
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
name|fieldContent
argument_list|,
name|OS
operator|.
name|NEWLINE
argument_list|)
return|;
block|}
return|return
name|WHITESPACE
operator|.
name|matcher
argument_list|(
name|fieldContent
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|)
return|;
block|}
DECL|method|format (StringBuilder fieldContent, String bibtexField)
specifier|public
name|String
name|format
parameter_list|(
name|StringBuilder
name|fieldContent
parameter_list|,
name|String
name|bibtexField
parameter_list|)
block|{
return|return
name|format
argument_list|(
name|fieldContent
operator|.
name|toString
argument_list|()
argument_list|,
name|bibtexField
argument_list|)
return|;
block|}
block|}
end_class

end_unit
