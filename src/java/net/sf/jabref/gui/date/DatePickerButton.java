begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2006 Raik Nagel<kiar@users.sourceforge.net>  All rights reserved.   Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:   * Redistributions of source code must retain the above copyright notice,   this list of conditions and the following disclaimer.  * Redistributions in binary form must reproduce the above copyright notice,   this list of conditions and the following disclaimer in the documentation   and/or other materials provided with the distribution.  * Neither the name of the author nor the names of its contributors may be   used to endorse or promote products derived from this software without   specific prior written permission.   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A  PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER  OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;  OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  */
end_comment

begin_comment
comment|// created by : r.nagel 19.04.2006
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : wrapper and service class for the DatePicker handling at the
end_comment

begin_comment
comment|//            EntryEditor
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// todo     :
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :  r.nagel 25.04.2006
end_comment

begin_comment
comment|//             check NullPointer at the actionPerformed methode
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
name|event
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
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
name|*
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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|DatePicker
name|datePicker
init|=
operator|new
name|DatePicker
argument_list|()
decl_stmt|;
DECL|field|editor
specifier|private
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
name|editor
operator|=
name|pEditor
expr_stmt|;
block|}
DECL|method|actionPerformed ( ActionEvent e )
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
name|Util
operator|.
name|easyDateFormat
argument_list|(
name|date
argument_list|)
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
return|return
name|datePicker
return|;
block|}
block|}
end_class

end_unit

