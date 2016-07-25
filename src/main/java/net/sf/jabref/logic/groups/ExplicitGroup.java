begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|importer
operator|.
name|fileformat
operator|.
name|ParseException
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|QuotedStringTokenizer
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|FieldName
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Select explicit bibtex entries. It is also known as static group.  *  * @author jzieren  */
end_comment

begin_class
DECL|class|ExplicitGroup
specifier|public
class|class
name|ExplicitGroup
extends|extends
name|KeywordGroup
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"ExplicitGroup:"
decl_stmt|;
DECL|field|legacyEntryKeys
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|legacyEntryKeys
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ExplicitGroup
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|ExplicitGroup (String name, GroupHierarchyType context, JabRefPreferences jabRefPreferences)
specifier|public
name|ExplicitGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|JabRefPreferences
name|jabRefPreferences
parameter_list|)
throws|throws
name|ParseException
block|{
name|super
argument_list|(
name|name
argument_list|,
name|FieldName
operator|.
name|GROUPS
argument_list|,
name|name
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
name|context
argument_list|,
name|jabRefPreferences
argument_list|)
expr_stmt|;
block|}
DECL|method|fromString (String s, JabRefPreferences jabRefPreferences)
specifier|public
specifier|static
name|ExplicitGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|JabRefPreferences
name|jabRefPreferences
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|ExplicitGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"ExplicitGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|ExplicitGroup
operator|.
name|ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|int
name|context
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
name|ExplicitGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|name
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumber
argument_list|(
name|context
argument_list|)
argument_list|,
name|jabRefPreferences
argument_list|)
decl_stmt|;
name|newGroup
operator|.
name|addLegacyEntryKeys
argument_list|(
name|tok
argument_list|)
expr_stmt|;
return|return
name|newGroup
return|;
block|}
comment|/**      * Called only when created fromString.      * JabRef used to store the entries of an explicit group in the serialization, e.g.      *  ExplicitGroup:GroupName\;0\;Key1\;Key2\;;      * This method exists for backwards compatibility.      */
DECL|method|addLegacyEntryKeys (QuotedStringTokenizer tok)
specifier|private
name|void
name|addLegacyEntryKeys
parameter_list|(
name|QuotedStringTokenizer
name|tok
parameter_list|)
block|{
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
decl_stmt|;
name|addLegacyEntryKey
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addLegacyEntryKey (String key)
specifier|public
name|void
name|addLegacyEntryKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|this
operator|.
name|legacyEntryKeys
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
try|try
block|{
name|ExplicitGroup
name|copy
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|getName
argument_list|()
argument_list|,
name|getContext
argument_list|()
argument_list|,
name|jabRefPreferences
argument_list|)
decl_stmt|;
name|copy
operator|.
name|legacyEntryKeys
operator|.
name|addAll
argument_list|(
name|legacyEntryKeys
argument_list|)
expr_stmt|;
return|return
name|copy
return|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|exception
parameter_list|)
block|{
comment|// this should never happen, because the constructor obviously succeeded in creating _this_ instance!
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Internal error in ExplicitGroup.deepCopy(). "
operator|+
literal|"Please report this on https://github.com/JabRef/jabref/issues"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|ExplicitGroup
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|ExplicitGroup
name|other
init|=
operator|(
name|ExplicitGroup
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|getName
argument_list|()
argument_list|,
name|other
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|getHierarchicalContext
argument_list|()
argument_list|,
name|other
operator|.
name|getHierarchicalContext
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|getLegacyEntryKeys
argument_list|()
argument_list|,
name|other
operator|.
name|getLegacyEntryKeys
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns a String representation of this group and its entries.      */
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|ExplicitGroup
operator|.
name|ID
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|getName
argument_list|()
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|)
operator|.
name|append
argument_list|(
name|getContext
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|)
expr_stmt|;
comment|// write legacy entry keys in well-defined order for CVS compatibility
name|Set
argument_list|<
name|String
argument_list|>
name|sortedKeys
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
name|sortedKeys
operator|.
name|addAll
argument_list|(
name|legacyEntryKeys
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|sortedKey
range|:
name|sortedKeys
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|sortedKey
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Remove all stored cite keys, resulting in an empty group.      */
DECL|method|clearLegacyEntryKeys ()
specifier|public
name|void
name|clearLegacyEntryKeys
parameter_list|()
block|{
name|legacyEntryKeys
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|ExplicitGroup
operator|.
name|getDescriptionForPreview
argument_list|()
return|;
block|}
DECL|method|getDescriptionForPreview ()
specifier|public
specifier|static
name|String
name|getDescriptionForPreview
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries based on manual assignment. "
operator|+
literal|"Entries can be assigned to this group by selecting them "
operator|+
literal|"then using either drag and drop or the context menu. "
operator|+
literal|"Entries can be removed from this group by selecting them "
operator|+
literal|"then using the context menu."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getShortDescription (boolean showDynamic)
specifier|public
name|String
name|getShortDescription
parameter_list|(
name|boolean
name|showDynamic
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"</b> -"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"static group"
argument_list|)
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"includes subgroups"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"refines supergroup"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getLegacyEntryKeys ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getLegacyEntryKeys
parameter_list|()
block|{
return|return
name|legacyEntryKeys
return|;
block|}
annotation|@
name|Override
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|ExplicitGroup
operator|.
name|ID
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

