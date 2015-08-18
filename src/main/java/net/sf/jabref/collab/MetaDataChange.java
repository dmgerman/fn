begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
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
name|gui
operator|.
name|BasePanel
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
name|BibtexDatabase
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
name|MetaData
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
name|undo
operator|.
name|NamedCompound
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
name|Vector
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

begin_comment
comment|/**  *   */
end_comment

begin_class
DECL|class|MetaDataChange
class|class
name|MetaDataChange
extends|extends
name|Change
block|{
specifier|private
specifier|static
specifier|final
name|int
DECL|field|ADD
name|ADD
init|=
literal|1
decl_stmt|;
DECL|field|REMOVE
specifier|private
specifier|static
specifier|final
name|int
name|REMOVE
init|=
literal|2
decl_stmt|;
DECL|field|MODIFY
specifier|private
specifier|static
specifier|final
name|int
name|MODIFY
init|=
literal|3
decl_stmt|;
DECL|field|tp
specifier|private
specifier|final
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|field|sp
specifier|private
specifier|final
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|tp
argument_list|)
decl_stmt|;
DECL|field|md
specifier|private
specifier|final
name|MetaData
name|md
decl_stmt|;
DECL|field|mdSecondary
specifier|private
specifier|final
name|MetaData
name|mdSecondary
decl_stmt|;
DECL|field|changes
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|MetaDataChangeUnit
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<
name|MetaDataChangeUnit
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|MetaDataChange (MetaData md, MetaData mdSecondary)
specifier|public
name|MetaDataChange
parameter_list|(
name|MetaData
name|md
parameter_list|,
name|MetaData
name|mdSecondary
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Metadata change"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|md
operator|=
name|md
expr_stmt|;
name|this
operator|.
name|mdSecondary
operator|=
name|mdSecondary
expr_stmt|;
name|tp
operator|.
name|setText
argument_list|(
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Metadata change"
argument_list|)
operator|+
literal|"</html>"
argument_list|)
expr_stmt|;
block|}
DECL|method|getChangeCount ()
specifier|public
name|int
name|getChangeCount
parameter_list|()
block|{
return|return
name|changes
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|insertMetaDataAddition (String key, Vector<String> value)
specifier|public
name|void
name|insertMetaDataAddition
parameter_list|(
name|String
name|key
parameter_list|,
name|Vector
argument_list|<
name|String
argument_list|>
name|value
parameter_list|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|MetaDataChangeUnit
argument_list|(
name|MetaDataChange
operator|.
name|ADD
argument_list|,
name|key
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|insertMetaDataRemoval (String key)
specifier|public
name|void
name|insertMetaDataRemoval
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|MetaDataChangeUnit
argument_list|(
name|MetaDataChange
operator|.
name|REMOVE
argument_list|,
name|key
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|insertMetaDataChange (String key, Vector<String> value)
specifier|public
name|void
name|insertMetaDataChange
parameter_list|(
name|String
name|key
parameter_list|,
name|Vector
argument_list|<
name|String
argument_list|>
name|value
parameter_list|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|MetaDataChangeUnit
argument_list|(
name|MetaDataChange
operator|.
name|MODIFY
argument_list|,
name|key
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Changes have been made to the following metadata elements"
argument_list|)
operator|+
literal|":<p>"
argument_list|)
decl_stmt|;
for|for
control|(
name|MetaDataChangeUnit
name|unit
range|:
name|changes
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<br>&nbsp;&nbsp;"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|unit
operator|.
name|key
argument_list|)
expr_stmt|;
comment|/*switch (unit.type) {                 case ADD:                     sb.append("<p>Added: "+unit.key);                     break;                 case REMOVE:                     sb.append("<p>Removed: "+unit.key);                     break;                 case MODIFY:                     sb.append("<p>Modified: "+unit.key);                     break;             }*/
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</html>"
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setText
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sp
return|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibtexDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibtexDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
for|for
control|(
name|MetaDataChangeUnit
name|unit
range|:
name|changes
control|)
block|{
switch|switch
condition|(
name|unit
operator|.
name|type
condition|)
block|{
case|case
name|ADD
case|:
name|md
operator|.
name|putData
argument_list|(
name|unit
operator|.
name|key
argument_list|,
name|unit
operator|.
name|value
argument_list|)
expr_stmt|;
name|mdSecondary
operator|.
name|putData
argument_list|(
name|unit
operator|.
name|key
argument_list|,
name|unit
operator|.
name|value
argument_list|)
expr_stmt|;
break|break;
case|case
name|REMOVE
case|:
name|md
operator|.
name|remove
argument_list|(
name|unit
operator|.
name|key
argument_list|)
expr_stmt|;
name|mdSecondary
operator|.
name|remove
argument_list|(
name|unit
operator|.
name|key
argument_list|)
expr_stmt|;
break|break;
case|case
name|MODIFY
case|:
name|md
operator|.
name|putData
argument_list|(
name|unit
operator|.
name|key
argument_list|,
name|unit
operator|.
name|value
argument_list|)
expr_stmt|;
name|mdSecondary
operator|.
name|putData
argument_list|(
name|unit
operator|.
name|key
argument_list|,
name|unit
operator|.
name|value
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
return|return
literal|true
return|;
block|}
DECL|class|MetaDataChangeUnit
specifier|static
class|class
name|MetaDataChangeUnit
block|{
DECL|field|type
specifier|final
name|int
name|type
decl_stmt|;
DECL|field|key
specifier|final
name|String
name|key
decl_stmt|;
DECL|field|value
specifier|final
name|Vector
argument_list|<
name|String
argument_list|>
name|value
decl_stmt|;
DECL|method|MetaDataChangeUnit (int type, String key, Vector<String> value)
specifier|public
name|MetaDataChangeUnit
parameter_list|(
name|int
name|type
parameter_list|,
name|String
name|key
parameter_list|,
name|Vector
argument_list|<
name|String
argument_list|>
name|value
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|key
operator|=
name|key
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

