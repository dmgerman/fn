begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

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
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
import|;
end_import

begin_class
DECL|class|CheckBoxMessage
specifier|public
class|class
name|CheckBoxMessage
extends|extends
name|JPanel
block|{
DECL|field|borderLayout1
name|BorderLayout
name|borderLayout1
init|=
operator|new
name|BorderLayout
argument_list|()
decl_stmt|;
DECL|field|cb
specifier|private
specifier|final
name|JCheckBox
name|cb
decl_stmt|;
DECL|method|CheckBoxMessage (String message, String cbText, boolean defaultValue)
specifier|public
name|CheckBoxMessage
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|cbText
parameter_list|,
name|boolean
name|defaultValue
parameter_list|)
block|{
name|cb
operator|=
operator|new
name|JCheckBox
argument_list|(
name|cbText
argument_list|,
name|defaultValue
argument_list|)
expr_stmt|;
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|message
operator|+
literal|'\n'
argument_list|)
decl_stmt|;
name|cb
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|10
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|cb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|cb
argument_list|)
expr_stmt|;
block|}
DECL|method|isSelected ()
specifier|public
name|boolean
name|isSelected
parameter_list|()
block|{
return|return
name|cb
operator|.
name|isSelected
argument_list|()
return|;
block|}
block|}
end_class

end_unit

