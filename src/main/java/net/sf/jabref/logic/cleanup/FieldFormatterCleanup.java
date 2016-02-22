begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.     This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.     You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|Collections
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
name|CleanupJob
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
specifier|final
name|CleanupJob
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
specifier|final
name|CleanupJob
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
specifier|final
name|CleanupJob
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
specifier|final
name|CleanupJob
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
specifier|final
name|CleanupJob
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
specifier|final
name|CleanupJob
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
specifier|final
name|CleanupJob
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
if|if
condition|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|field
argument_list|)
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
name|oldValue
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
condition|)
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
else|else
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
name|Collections
operator|.
name|singletonList
argument_list|(
name|change
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

