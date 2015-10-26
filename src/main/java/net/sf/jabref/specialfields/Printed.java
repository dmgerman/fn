begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
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
name|javax
operator|.
name|swing
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
name|gui
operator|.
name|IconTheme
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

begin_class
DECL|class|Printed
specifier|public
class|class
name|Printed
extends|extends
name|SpecialField
block|{
DECL|field|INSTANCE
specifier|private
specifier|static
name|Printed
name|INSTANCE
decl_stmt|;
DECL|method|Printed ()
specifier|private
name|Printed
parameter_list|()
block|{
name|ArrayList
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// DO NOT TRANSLATE "printed" as this makes the produced .bib files non portable
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"printed"
argument_list|,
literal|"togglePrinted"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toogle print status"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PRINTED
operator|.
name|getSmallIcon
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toogle print status"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setValues
argument_list|(
name|values
argument_list|)
expr_stmt|;
name|TEXT_DONE_PATTERN
operator|=
literal|"Toggled print status for %0 entries"
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRINTED
return|;
block|}
DECL|method|getInstance ()
specifier|public
specifier|static
name|Printed
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|Printed
operator|.
name|INSTANCE
operator|==
literal|null
condition|)
block|{
name|Printed
operator|.
name|INSTANCE
operator|=
operator|new
name|Printed
argument_list|()
expr_stmt|;
block|}
return|return
name|Printed
operator|.
name|INSTANCE
return|;
block|}
annotation|@
name|Override
DECL|method|getRepresentingIcon ()
specifier|public
name|Icon
name|getRepresentingIcon
parameter_list|()
block|{
return|return
name|this
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getIcon
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getToolTip ()
specifier|public
name|String
name|getToolTip
parameter_list|()
block|{
return|return
name|this
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getToolTipText
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getMenuString ()
specifier|public
name|String
name|getMenuString
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Printed"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|isSingleValueField ()
specifier|public
name|boolean
name|isSingleValueField
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

