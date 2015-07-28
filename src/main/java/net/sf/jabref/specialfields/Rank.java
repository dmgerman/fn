begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_class
DECL|class|Rank
specifier|public
specifier|abstract
class|class
name|Rank
extends|extends
name|SpecialField
block|{
DECL|method|Rank ()
name|Rank
parameter_list|()
block|{
name|TEXT_DONE_PATTERN
operator|=
literal|"Set rank to '%0' for %1 entries"
expr_stmt|;
block|}
DECL|method|getInstance ()
specifier|public
specifier|static
name|Rank
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_RANKING_COMPACT
argument_list|)
condition|)
block|{
return|return
name|RankCompact
operator|.
name|getInstance
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|RankExtended
operator|.
name|getInstance
argument_list|()
return|;
block|}
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
name|FIELDNAME_RANKING
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Rank"
argument_list|)
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Rank"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

