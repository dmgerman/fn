begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.cleanup
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
package|;
end_package

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
name|List
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
name|formatter
operator|.
name|Formatters
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
name|formatter
operator|.
name|IdentityFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|HtmlToLatexFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|HtmlToUnicodeFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeDateFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeMonthFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizePagesFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|OrdinalsToSuperscriptFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnicodeToLatexFormatter
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
name|layout
operator|.
name|format
operator|.
name|LatexToUnicodeFormatter
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
name|layout
operator|.
name|format
operator|.
name|ReplaceUnicodeLigaturesFormatter
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|cleanup
operator|.
name|FieldFormatterCleanups
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
name|cleanup
operator|.
name|Formatter
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
name|FieldFactory
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
name|InternalField
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
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|Cleanups
specifier|public
class|class
name|Cleanups
block|{
DECL|field|DEFAULT_SAVE_ACTIONS
specifier|public
specifier|static
specifier|final
name|FieldFormatterCleanups
name|DEFAULT_SAVE_ACTIONS
decl_stmt|;
DECL|field|RECOMMEND_BIBTEX_ACTIONS
specifier|public
specifier|static
specifier|final
name|FieldFormatterCleanups
name|RECOMMEND_BIBTEX_ACTIONS
decl_stmt|;
DECL|field|RECOMMEND_BIBLATEX_ACTIONS
specifier|public
specifier|static
specifier|final
name|FieldFormatterCleanups
name|RECOMMEND_BIBLATEX_ACTIONS
decl_stmt|;
static|static
block|{
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|defaultFormatters
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|defaultFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
operator|new
name|NormalizePagesFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|defaultFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|StandardField
operator|.
name|DATE
argument_list|,
operator|new
name|NormalizeDateFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|defaultFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
operator|new
name|NormalizeMonthFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|defaultFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|InternalField
operator|.
name|INTERNAL_ALL_TEXT_FIELDS_FIELD
argument_list|,
operator|new
name|ReplaceUnicodeLigaturesFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|DEFAULT_SAVE_ACTIONS
operator|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|false
argument_list|,
name|defaultFormatters
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|recommendedBibTeXFormatters
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|recommendedBibTeXFormatters
operator|.
name|addAll
argument_list|(
name|defaultFormatters
argument_list|)
expr_stmt|;
name|recommendedBibTeXFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|InternalField
operator|.
name|INTERNAL_ALL_TEXT_FIELDS_FIELD
argument_list|,
operator|new
name|HtmlToLatexFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|recommendedBibTeXFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|InternalField
operator|.
name|INTERNAL_ALL_TEXT_FIELDS_FIELD
argument_list|,
operator|new
name|UnicodeToLatexFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|recommendedBibTeXFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|InternalField
operator|.
name|INTERNAL_ALL_TEXT_FIELDS_FIELD
argument_list|,
operator|new
name|OrdinalsToSuperscriptFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|RECOMMEND_BIBTEX_ACTIONS
operator|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|false
argument_list|,
name|recommendedBibTeXFormatters
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|recommendedBiblatexFormatters
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|recommendedBiblatexFormatters
operator|.
name|addAll
argument_list|(
name|defaultFormatters
argument_list|)
expr_stmt|;
name|recommendedBiblatexFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
operator|new
name|HtmlToUnicodeFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|recommendedBiblatexFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|InternalField
operator|.
name|INTERNAL_ALL_TEXT_FIELDS_FIELD
argument_list|,
operator|new
name|LatexToUnicodeFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// DO NOT ADD OrdinalsToSuperscriptFormatter here, because this causes issues. See https://github.com/JabRef/jabref/issues/2596.
name|RECOMMEND_BIBLATEX_ACTIONS
operator|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|false
argument_list|,
name|recommendedBiblatexFormatters
argument_list|)
expr_stmt|;
block|}
DECL|method|Cleanups ()
specifier|private
name|Cleanups
parameter_list|()
block|{     }
DECL|method|getBuiltInFormatters ()
specifier|public
specifier|static
name|List
argument_list|<
name|Formatter
argument_list|>
name|getBuiltInFormatters
parameter_list|()
block|{
return|return
name|Formatters
operator|.
name|getAll
argument_list|()
return|;
block|}
DECL|method|parse (String formatterString)
specifier|public
specifier|static
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|parse
parameter_list|(
name|String
name|formatterString
parameter_list|)
block|{
if|if
condition|(
operator|(
name|formatterString
operator|==
literal|null
operator|)
operator|||
name|formatterString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// no save actions defined in the meta data
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actions
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|//read concrete actions
name|int
name|startIndex
init|=
literal|0
decl_stmt|;
comment|// first remove all newlines for easier parsing
name|String
name|remainingString
init|=
name|formatterString
decl_stmt|;
name|remainingString
operator|=
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
name|remainingString
argument_list|,
literal|""
argument_list|)
expr_stmt|;
try|try
block|{
while|while
condition|(
name|startIndex
operator|<
name|formatterString
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// read the field name
name|int
name|currentIndex
init|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|'['
argument_list|)
decl_stmt|;
name|String
name|fieldKey
init|=
name|remainingString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|currentIndex
argument_list|)
decl_stmt|;
name|int
name|endIndex
init|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|)
decl_stmt|;
name|startIndex
operator|+=
name|endIndex
operator|+
literal|1
expr_stmt|;
comment|//read each formatter
name|int
name|tokenIndex
init|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
decl_stmt|;
do|do
block|{
name|boolean
name|doBreak
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
name|tokenIndex
operator|==
operator|-
literal|1
operator|)
operator|||
operator|(
name|tokenIndex
operator|>
name|endIndex
operator|)
condition|)
block|{
name|tokenIndex
operator|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
name|doBreak
operator|=
literal|true
expr_stmt|;
block|}
name|String
name|formatterKey
init|=
name|remainingString
operator|.
name|substring
argument_list|(
name|currentIndex
operator|+
literal|1
argument_list|,
name|tokenIndex
argument_list|)
decl_stmt|;
name|actions
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldFactory
operator|.
name|parseField
argument_list|(
name|fieldKey
argument_list|)
argument_list|,
name|getFormatterFromString
argument_list|(
name|formatterKey
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|remainingString
operator|=
name|remainingString
operator|.
name|substring
argument_list|(
name|tokenIndex
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|remainingString
operator|.
name|startsWith
argument_list|(
literal|"]"
argument_list|)
operator|||
name|doBreak
condition|)
block|{
break|break;
block|}
name|tokenIndex
operator|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|currentIndex
operator|=
operator|-
literal|1
expr_stmt|;
block|}
do|while
condition|(
literal|true
condition|)
do|;
block|}
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|ignore
parameter_list|)
block|{
comment|// if this exception occurs, the remaining part of the save actions string is invalid.
comment|// Thus we stop parsing and take what we have parsed until now
return|return
name|actions
return|;
block|}
return|return
name|actions
return|;
block|}
DECL|method|parse (List<String> formatterMetaList)
specifier|public
specifier|static
name|FieldFormatterCleanups
name|parse
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|formatterMetaList
parameter_list|)
block|{
if|if
condition|(
operator|(
name|formatterMetaList
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|formatterMetaList
operator|.
name|size
argument_list|()
operator|>=
literal|2
operator|)
condition|)
block|{
name|boolean
name|enablementStatus
init|=
name|FieldFormatterCleanups
operator|.
name|ENABLED
operator|.
name|equals
argument_list|(
name|formatterMetaList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|formatterString
init|=
name|formatterMetaList
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
return|return
operator|new
name|FieldFormatterCleanups
argument_list|(
name|enablementStatus
argument_list|,
name|parse
argument_list|(
name|formatterString
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// return default actions
return|return
name|DEFAULT_SAVE_ACTIONS
return|;
block|}
block|}
DECL|method|getFormatterFromString (String formatterName)
specifier|private
specifier|static
name|Formatter
name|getFormatterFromString
parameter_list|(
name|String
name|formatterName
parameter_list|)
block|{
for|for
control|(
name|Formatter
name|formatter
range|:
name|getBuiltInFormatters
argument_list|()
control|)
block|{
if|if
condition|(
name|formatterName
operator|.
name|equals
argument_list|(
name|formatter
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|formatter
return|;
block|}
block|}
return|return
operator|new
name|IdentityFormatter
argument_list|()
return|;
block|}
block|}
end_class

end_unit

