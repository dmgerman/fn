begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
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
name|Arrays
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|HTMLConverter
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
name|FieldChange
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
name|formatter
operator|.
name|BibtexFieldFormatters
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
name|formatter
operator|.
name|Formatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|LatexFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|MonthFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnitFormatter
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
name|formatter
operator|.
name|casechanger
operator|.
name|CaseKeeper
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

begin_comment
comment|/**  * Formats a given entry field with the specified formatter.  */
end_comment

begin_class
DECL|class|FieldFormatterCleanup
specifier|public
class|class
name|FieldFormatterCleanup
implements|implements
name|Cleaner
block|{
DECL|field|field
specifier|private
specifier|final
name|String
name|field
decl_stmt|;
DECL|field|formatter
specifier|private
specifier|final
name|Formatter
name|formatter
decl_stmt|;
DECL|field|PAGE_NUMBERS
specifier|public
specifier|static
name|Cleaner
name|PAGE_NUMBERS
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"pages"
argument_list|,
name|BibtexFieldFormatters
operator|.
name|PAGE_NUMBERS
argument_list|)
decl_stmt|;
DECL|field|DATES
specifier|public
specifier|static
name|Cleaner
name|DATES
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"date"
argument_list|,
name|BibtexFieldFormatters
operator|.
name|DATE
argument_list|)
decl_stmt|;
DECL|field|MONTH
specifier|public
specifier|static
name|Cleaner
name|MONTH
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"month"
argument_list|,
operator|new
name|MonthFormatter
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|TITLE_CASE
specifier|public
specifier|static
name|Cleaner
name|TITLE_CASE
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|CaseKeeper
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|TITLE_UNITS
specifier|public
specifier|static
name|Cleaner
name|TITLE_UNITS
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|UnitFormatter
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|TITLE_LATEX
specifier|public
specifier|static
name|Cleaner
name|TITLE_LATEX
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LatexFormatter
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|TITLE_HTML
specifier|public
specifier|static
name|Cleaner
name|TITLE_HTML
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|HTMLConverter
argument_list|()
argument_list|)
decl_stmt|;
DECL|method|FieldFormatterCleanup (String field, Formatter formatter)
specifier|public
name|FieldFormatterCleanup
parameter_list|(
name|String
name|field
parameter_list|,
name|Formatter
name|formatter
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|formatter
operator|=
name|formatter
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|cleanup (BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|String
name|oldValue
init|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldValue
operator|==
literal|null
condition|)
block|{
comment|// Not set -> nothing to do
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
comment|// Run formatter
name|String
name|newValue
init|=
name|formatter
operator|.
name|format
argument_list|(
name|oldValue
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|oldValue
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
name|FieldChange
name|change
init|=
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|FieldChange
index|[]
block|{
name|change
block|}
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

