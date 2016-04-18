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
name|bibtexfields
operator|.
name|UnicodeToLatexFormatter
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
comment|/**  * Converts Unicode characters to LaTeX code.  */
end_comment

begin_class
DECL|class|UnicodeCleanup
specifier|public
class|class
name|UnicodeCleanup
implements|implements
name|CleanupJob
block|{
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
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"title"
argument_list|,
literal|"author"
argument_list|,
literal|"abstract"
argument_list|)
decl_stmt|;
specifier|final
name|UnicodeToLatexFormatter
name|unicodeConverter
init|=
operator|new
name|UnicodeToLatexFormatter
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
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
continue|continue;
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
name|String
name|newValue
init|=
name|unicodeConverter
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
name|changes
operator|.
name|add
argument_list|(
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
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|changes
return|;
block|}
block|}
end_class

end_unit

