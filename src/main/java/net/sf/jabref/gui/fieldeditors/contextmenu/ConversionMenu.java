begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015-2016 JabRef contributors.     Copyright (C) 2015-2016 Oscar Gustafsson.      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.fieldeditors.contextmenu
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|contextmenu
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
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
name|Formatters
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

begin_comment
comment|/**  * @author Oscar Gustafsson  *  * Menu to show up on right-click in a text field for converting text formats  */
end_comment

begin_class
DECL|class|ConversionMenu
specifier|public
class|class
name|ConversionMenu
extends|extends
name|JMenu
block|{
DECL|method|ConversionMenu (JTextComponent opener)
specifier|public
name|ConversionMenu
parameter_list|(
name|JTextComponent
name|opener
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Convert"
argument_list|)
argument_list|)
expr_stmt|;
comment|// create menu items, one for each case changer
for|for
control|(
name|Formatter
name|converter
range|:
name|Formatters
operator|.
name|CONVERTERS
control|)
block|{
name|JMenuItem
name|menuItem
init|=
operator|new
name|JMenuItem
argument_list|(
name|converter
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|menuItem
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|opener
operator|.
name|setText
argument_list|(
name|converter
operator|.
name|format
argument_list|(
name|opener
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

