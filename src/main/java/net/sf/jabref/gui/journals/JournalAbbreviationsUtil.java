begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|journals
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
name|logic
operator|.
name|journals
operator|.
name|Abbreviation
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|DefaultTableModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableModel
import|;
end_import

begin_class
DECL|class|JournalAbbreviationsUtil
specifier|public
class|class
name|JournalAbbreviationsUtil
block|{
DECL|method|getTableModel (Collection<Abbreviation> abbreviations)
specifier|public
specifier|static
name|TableModel
name|getTableModel
parameter_list|(
name|Collection
argument_list|<
name|Abbreviation
argument_list|>
name|abbreviations
parameter_list|)
block|{
name|Object
index|[]
index|[]
name|cells
init|=
operator|new
name|Object
index|[
name|abbreviations
operator|.
name|size
argument_list|()
index|]
index|[
literal|2
index|]
decl_stmt|;
name|int
name|row
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|abbreviations
control|)
block|{
name|cells
index|[
name|row
index|]
index|[
literal|0
index|]
operator|=
name|abbreviation
operator|.
name|getName
argument_list|()
expr_stmt|;
name|cells
index|[
name|row
index|]
index|[
literal|1
index|]
operator|=
name|abbreviation
operator|.
name|getIsoAbbreviation
argument_list|()
expr_stmt|;
name|row
operator|++
expr_stmt|;
block|}
return|return
operator|new
name|DefaultTableModel
argument_list|(
name|cells
argument_list|,
operator|new
name|Object
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Full name"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviation"
argument_list|)
block|}
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row1
parameter_list|,
name|int
name|column
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|;
block|}
block|}
end_class

end_unit

