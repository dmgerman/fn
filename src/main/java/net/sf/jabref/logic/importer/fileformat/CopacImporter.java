begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|regex
operator|.
name|Pattern
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
name|logic
operator|.
name|importer
operator|.
name|Importer
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
name|logic
operator|.
name|importer
operator|.
name|ParserResult
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
name|logic
operator|.
name|util
operator|.
name|FileExtensions
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_comment
comment|/**  * Importer for COPAC format.  *  * Documentation can be found online at:  *  * http://copac.ac.uk/faq/#format  */
end_comment

begin_class
DECL|class|CopacImporter
specifier|public
class|class
name|CopacImporter
extends|extends
name|Importer
block|{
DECL|field|COPAC_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|COPAC_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^\\s*TI- "
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Copac"
return|;
block|}
annotation|@
name|Override
DECL|method|getExtensions ()
specifier|public
name|FileExtensions
name|getExtensions
parameter_list|()
block|{
return|return
name|FileExtensions
operator|.
name|COPAC
return|;
block|}
annotation|@
name|Override
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
literal|"cpc"
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"Importer for COPAC format."
return|;
block|}
annotation|@
name|Override
DECL|method|isRecognizedFormat (BufferedReader reader)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|str
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|CopacImporter
operator|.
name|COPAC_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|importDatabase (BufferedReader reader)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|entries
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// Preprocess entries
name|String
name|str
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|<
literal|4
condition|)
block|{
continue|continue;
block|}
name|String
name|code
init|=
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"    "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|str
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// begining of a new item
if|if
condition|(
literal|"TI- "
operator|.
name|equals
argument_list|(
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|results
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|entry
range|:
name|entries
control|)
block|{
comment|// Copac does not contain enough information on the type of the
comment|// document. A book is assumed.
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
literal|"book"
argument_list|)
decl_stmt|;
name|String
index|[]
name|lines
init|=
name|entry
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|line1
range|:
name|lines
control|)
block|{
name|String
name|line
init|=
name|line1
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|line
operator|.
name|length
argument_list|()
operator|<
literal|4
condition|)
block|{
continue|continue;
block|}
name|String
name|code
init|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"TI- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|TITLE
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AU- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|" and "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PY- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|YEAR
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PU- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|PUBLISHER
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SE- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|SERIES
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"IS- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|ISBN
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"KW- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"NT- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|FieldName
operator|.
name|NOTE
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PD- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"physicaldimensions"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DT- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"documenttype"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|code
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|results
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|ParserResult
argument_list|(
name|results
argument_list|)
return|;
block|}
DECL|method|setOrAppend (BibEntry b, String field, String value, String separator)
specifier|private
specifier|static
name|void
name|setOrAppend
parameter_list|(
name|BibEntry
name|b
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|b
operator|.
name|hasField
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|b
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|b
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|get
argument_list|()
operator|+
name|separator
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|b
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

