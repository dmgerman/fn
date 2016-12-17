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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|AuthorList
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
comment|/**  * INSPEC format importer.  */
end_comment

begin_class
DECL|class|InspecImporter
specifier|public
class|class
name|InspecImporter
extends|extends
name|Importer
block|{
DECL|field|INSPEC_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|INSPEC_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Record.*INSPEC.*"
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
literal|"INSPEC"
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
name|INSPEC
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
literal|"INSPEC format importer."
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
comment|// Our strategy is to look for the "PY<year>" line.
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
name|INSPEC_PATTERN
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
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
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
literal|2
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|str
operator|.
name|indexOf
argument_list|(
literal|"Record"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"__::__"
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"__NEWFIELD__"
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
block|}
name|String
index|[]
name|entries
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"__::__"
argument_list|)
decl_stmt|;
name|String
name|type
init|=
literal|""
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|h
init|=
operator|new
name|HashMap
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
if|if
condition|(
name|entry
operator|.
name|indexOf
argument_list|(
literal|"Record"
argument_list|)
operator|!=
literal|0
condition|)
block|{
continue|continue;
block|}
name|h
operator|.
name|clear
argument_list|()
expr_stmt|;
name|String
index|[]
name|fields
init|=
name|entry
operator|.
name|split
argument_list|(
literal|"__NEWFIELD__"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|fields
control|)
block|{
name|String
name|f3
init|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|String
name|frest
init|=
name|s
operator|.
name|substring
argument_list|(
literal|5
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"TI"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PY"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AU"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|AuthorList
operator|.
name|fixAuthorLastNameFirst
argument_list|(
name|frest
operator|.
name|replace
argument_list|(
literal|",-"
argument_list|,
literal|", "
argument_list|)
operator|.
name|replace
argument_list|(
literal|";"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AB"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"ID"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SO"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|int
name|m
init|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|>=
literal|0
condition|)
block|{
name|String
name|jr
init|=
name|frest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|m
argument_list|)
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|jr
operator|.
name|replace
argument_list|(
literal|"-"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
name|frest
operator|=
name|frest
operator|.
name|substring
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|m
operator|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|>=
literal|5
condition|)
block|{
name|String
name|yr
init|=
name|frest
operator|.
name|substring
argument_list|(
name|m
operator|-
literal|5
argument_list|,
name|m
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|yr
argument_list|)
expr_stmt|;
name|frest
operator|=
name|frest
operator|.
name|substring
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|m
operator|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|>=
literal|0
condition|)
block|{
name|String
name|pg
init|=
name|frest
operator|.
name|substring
argument_list|(
name|m
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|pg
argument_list|)
expr_stmt|;
name|String
name|vol
init|=
name|frest
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|m
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|VOLUME
argument_list|,
name|vol
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
elseif|else
if|if
condition|(
literal|"RT"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|frest
operator|=
name|frest
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
literal|"Journal-Paper"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"Conference-Paper"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
operator|||
literal|"Conference-Paper; Journal-Paper"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"inproceedings"
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
name|frest
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
name|type
argument_list|)
decl_stmt|;
name|b
operator|.
name|setField
argument_list|(
name|h
argument_list|)
expr_stmt|;
name|bibitems
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
name|bibitems
argument_list|)
return|;
block|}
block|}
end_class

end_unit

