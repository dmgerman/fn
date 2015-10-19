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
name|util
operator|.
name|ArrayList
import|;
end_import

begin_class
DECL|class|Rank
specifier|public
class|class
name|Rank
extends|extends
name|SpecialField
block|{
DECL|field|INSTANCE
specifier|private
specifier|static
name|Rank
name|INSTANCE
decl_stmt|;
DECL|method|Rank ()
specifier|private
name|Rank
parameter_list|()
block|{
name|TEXT_DONE_PATTERN
operator|=
literal|"Set rank to '%0' for %1 entries"
expr_stmt|;
name|ArrayList
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
init|=
operator|new
name|ArrayList
argument_list|<
name|SpecialFieldValue
argument_list|>
argument_list|()
decl_stmt|;
comment|//lab.setName("i");
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|null
argument_list|,
literal|"clearRank"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear rank"
argument_list|)
argument_list|,
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No rank information"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// DO NOT TRANSLATE "rank1" etc. as this makes the .bib files non portable
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"rank1"
argument_list|,
literal|"setRank1"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to one star"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"rank1"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"One star"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"rank2"
argument_list|,
literal|"setRank2"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to two stars"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"rank2"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Two stars"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"rank3"
argument_list|,
literal|"setRank3"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to three stars"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"rank3"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Three stars"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"rank4"
argument_list|,
literal|"setRank4"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to four stars"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"rank4"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Four stars"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"rank5"
argument_list|,
literal|"setRank5"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to five stars"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"rank5"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Five stars"
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
name|Rank
operator|.
name|INSTANCE
operator|==
literal|null
condition|)
block|{
name|Rank
operator|.
name|INSTANCE
operator|=
operator|new
name|Rank
argument_list|()
expr_stmt|;
block|}
return|return
name|Rank
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
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"ranking"
argument_list|)
return|;
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
name|Localization
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
name|Localization
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

