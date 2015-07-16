begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 Raik Nagel     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|// function : wrapper and service class for the DatePicker handling at the
end_comment

begin_comment
comment|//            EntryEditor
end_comment

begin_package
DECL|package|net.sf.jabref.gui.date
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|date
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|EasyDateFormat
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
name|FieldEditor
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
name|FocusRequester
import|;
end_import

begin_import
import|import
name|com
operator|.
name|michaelbaranov
operator|.
name|microba
operator|.
name|calendar
operator|.
name|DatePicker
import|;
end_import

begin_class
DECL|class|DatePickerButton
specifier|public
class|class
name|DatePickerButton
implements|implements
name|ActionListener
block|{
DECL|field|datePicker
specifier|private
specifier|final
name|DatePicker
name|datePicker
init|=
operator|new
name|DatePicker
argument_list|()
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|FieldEditor
name|editor
decl_stmt|;
DECL|method|DatePickerButton (FieldEditor pEditor)
specifier|public
name|DatePickerButton
parameter_list|(
name|FieldEditor
name|pEditor
parameter_list|)
block|{
name|datePicker
operator|.
name|showButtonOnly
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|datePicker
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|datePicker
operator|.
name|setShowTodayButton
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|datePicker
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|editor
operator|=
name|pEditor
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Date
name|date
init|=
name|datePicker
operator|.
name|getDate
argument_list|()
decl_stmt|;
if|if
condition|(
name|date
operator|!=
literal|null
condition|)
block|{
name|editor
operator|.
name|setText
argument_list|(
operator|new
name|EasyDateFormat
argument_list|()
operator|.
name|getDateAt
argument_list|(
name|date
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set focus to editor component after changing its text:
operator|new
name|FocusRequester
argument_list|(
name|editor
operator|.
name|getTextComponent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getDatePicker ()
specifier|public
name|JComponent
name|getDatePicker
parameter_list|()
block|{
comment|//return datePicker;
return|return
name|panel
return|;
block|}
block|}
end_class

end_unit

