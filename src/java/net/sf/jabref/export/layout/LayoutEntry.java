begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Morten O. Alver  All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
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
name|Map
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
name|BibtexEntry
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
name|NameFormatterTab
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
name|Util
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|NameFormat
import|;
end_import

begin_import
import|import
name|wsi
operator|.
name|ra
operator|.
name|tool
operator|.
name|WSITools
import|;
end_import

begin_import
import|import
name|wsi
operator|.
name|ra
operator|.
name|types
operator|.
name|StringInt
import|;
end_import

begin_comment
comment|/**  * DOCUMENT ME!  *   * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|LayoutEntry
specifier|public
class|class
name|LayoutEntry
block|{
comment|// ~ Instance fields
comment|// ////////////////////////////////////////////////////////
DECL|field|option
specifier|private
name|LayoutFormatter
index|[]
name|option
decl_stmt|;
DECL|field|text
specifier|private
name|String
name|text
decl_stmt|;
DECL|field|layoutEntries
specifier|private
name|LayoutEntry
index|[]
name|layoutEntries
decl_stmt|;
DECL|field|type
specifier|private
name|int
name|type
decl_stmt|;
DECL|field|classPrefix
specifier|private
name|String
name|classPrefix
decl_stmt|;
comment|// ~ Constructors
comment|// ///////////////////////////////////////////////////////////
DECL|method|LayoutEntry (StringInt si, String classPrefix_)
specifier|public
name|LayoutEntry
parameter_list|(
name|StringInt
name|si
parameter_list|,
name|String
name|classPrefix_
parameter_list|)
throws|throws
name|Exception
block|{
name|type
operator|=
name|si
operator|.
name|i
expr_stmt|;
name|classPrefix
operator|=
name|classPrefix_
expr_stmt|;
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
condition|)
block|{
name|text
operator|=
name|si
operator|.
name|s
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
condition|)
block|{
name|text
operator|=
name|si
operator|.
name|s
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
condition|)
block|{ 		}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
condition|)
block|{ 		}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
condition|)
block|{
name|Vector
name|v
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|WSITools
operator|.
name|tokenize
argument_list|(
name|v
argument_list|,
name|si
operator|.
name|s
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
if|if
condition|(
name|v
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|text
operator|=
operator|(
name|String
operator|)
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|=
operator|(
operator|(
name|String
operator|)
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|// try
comment|// {
name|option
operator|=
name|getOptionalLayout
argument_list|(
operator|(
name|String
operator|)
name|v
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|classPrefix
argument_list|)
expr_stmt|;
comment|// }
comment|// catch (Exception e)
comment|// {
comment|// e.printStackTrace();
comment|// }
block|}
block|}
comment|// else if (si.i == LayoutHelper.IS_OPTION_FIELD_PARAM)
comment|// {
comment|// }
block|}
DECL|method|LayoutEntry (Vector parsedEntries, String classPrefix_, int layoutType)
specifier|public
name|LayoutEntry
parameter_list|(
name|Vector
name|parsedEntries
parameter_list|,
name|String
name|classPrefix_
parameter_list|,
name|int
name|layoutType
parameter_list|)
throws|throws
name|Exception
block|{
name|classPrefix
operator|=
name|classPrefix_
expr_stmt|;
name|String
name|blockStart
init|=
literal|null
decl_stmt|;
name|String
name|blockEnd
init|=
literal|null
decl_stmt|;
name|StringInt
name|si
decl_stmt|;
name|Vector
name|blockEntries
init|=
literal|null
decl_stmt|;
name|Vector
name|tmpEntries
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|LayoutEntry
name|le
decl_stmt|;
name|si
operator|=
operator|(
name|StringInt
operator|)
name|parsedEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|blockStart
operator|=
name|si
operator|.
name|s
expr_stmt|;
name|si
operator|=
operator|(
name|StringInt
operator|)
name|parsedEntries
operator|.
name|get
argument_list|(
name|parsedEntries
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|blockEnd
operator|=
name|si
operator|.
name|s
expr_stmt|;
if|if
condition|(
operator|!
name|blockStart
operator|.
name|equals
argument_list|(
name|blockEnd
argument_list|)
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Field start and end entry must be equal."
argument_list|)
expr_stmt|;
block|}
name|type
operator|=
name|layoutType
expr_stmt|;
name|text
operator|=
name|si
operator|.
name|s
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
operator|(
name|parsedEntries
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|si
operator|=
operator|(
name|StringInt
operator|)
name|parsedEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// System.out.println("PARSED-ENTRY: "+si.s+"="+si.i);
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
condition|)
block|{ 			}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
condition|)
block|{ 			}
elseif|else
if|if
condition|(
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
operator|)
condition|)
block|{
name|blockEntries
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|blockStart
operator|=
name|si
operator|.
name|s
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
operator|)
condition|)
block|{
if|if
condition|(
name|blockStart
operator|.
name|equals
argument_list|(
name|si
operator|.
name|s
argument_list|)
condition|)
block|{
name|blockEntries
operator|.
name|add
argument_list|(
name|si
argument_list|)
expr_stmt|;
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
condition|)
name|le
operator|=
operator|new
name|LayoutEntry
argument_list|(
name|blockEntries
argument_list|,
name|classPrefix
argument_list|,
name|LayoutHelper
operator|.
name|IS_GROUP_START
argument_list|)
expr_stmt|;
else|else
name|le
operator|=
operator|new
name|LayoutEntry
argument_list|(
name|blockEntries
argument_list|,
name|classPrefix
argument_list|,
name|LayoutHelper
operator|.
name|IS_FIELD_START
argument_list|)
expr_stmt|;
name|tmpEntries
operator|.
name|add
argument_list|(
name|le
argument_list|)
expr_stmt|;
name|blockEntries
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Nested field entries are not implemented !!!"
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
condition|)
block|{ 			}
comment|// else if (si.i == LayoutHelper.IS_OPTION_FIELD_PARAM)
comment|// {
comment|// }
if|if
condition|(
name|blockEntries
operator|==
literal|null
condition|)
block|{
comment|// System.out.println("BLOCK ADD: "+si.s+"="+si.i);
name|tmpEntries
operator|.
name|add
argument_list|(
operator|new
name|LayoutEntry
argument_list|(
name|si
argument_list|,
name|classPrefix
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|blockEntries
operator|.
name|add
argument_list|(
name|si
argument_list|)
expr_stmt|;
block|}
block|}
name|layoutEntries
operator|=
operator|new
name|LayoutEntry
index|[
name|tmpEntries
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tmpEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|layoutEntries
index|[
name|i
index|]
operator|=
operator|(
name|LayoutEntry
operator|)
name|tmpEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// System.out.println(layoutEntries[i].text);
block|}
block|}
DECL|method|doLayout (BibtexEntry bibtex, BibtexDatabase database)
specifier|public
name|String
name|doLayout
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
case|:
return|return
name|text
return|;
case|case
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
case|:
return|return
name|getField
argument_list|(
name|bibtex
argument_list|,
name|text
argument_list|,
name|database
argument_list|)
return|;
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_START
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_START
case|:
block|{
name|String
name|field
init|=
name|getField
argument_list|(
name|bibtex
argument_list|,
name|text
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|field
operator|==
literal|null
operator|)
operator|||
operator|(
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
operator|)
operator|&&
operator|(
name|field
operator|.
name|equalsIgnoreCase
argument_list|(
name|LayoutHelper
operator|.
name|getCurrentGroup
argument_list|()
argument_list|)
operator|)
operator|)
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
condition|)
block|{
name|LayoutHelper
operator|.
name|setCurrentGroup
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
decl_stmt|;
name|String
name|fieldText
decl_stmt|;
name|boolean
name|previousSkipped
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|layoutEntries
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fieldText
operator|=
name|layoutEntries
index|[
name|i
index|]
operator|.
name|doLayout
argument_list|(
name|bibtex
argument_list|,
name|database
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|layoutEntries
operator|.
name|length
condition|)
block|{
if|if
condition|(
name|layoutEntries
index|[
name|i
operator|+
literal|1
index|]
operator|.
name|doLayout
argument_list|(
name|bibtex
argument_list|,
name|database
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|i
operator|++
expr_stmt|;
name|previousSkipped
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
block|}
block|}
else|else
block|{
comment|// if previous was skipped --> remove leading line
comment|// breaks
if|if
condition|(
name|previousSkipped
condition|)
block|{
name|int
name|eol
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|eol
operator|<
name|fieldText
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|eol
argument_list|)
operator|==
literal|'\n'
operator|)
operator|||
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|eol
argument_list|)
operator|==
literal|'\r'
operator|)
operator|)
condition|)
block|{
name|eol
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|eol
operator|<
name|fieldText
operator|.
name|length
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|fieldText
operator|.
name|substring
argument_list|(
name|eol
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// System.out.println("ENTRY-BLOCK: " +
comment|// layoutEntries[i].doLayout(bibtex));
name|sb
operator|.
name|append
argument_list|(
name|fieldText
argument_list|)
expr_stmt|;
block|}
block|}
name|previousSkipped
operator|=
literal|false
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_END
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_END
case|:
return|return
literal|""
return|;
case|case
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
case|:
block|{
name|String
name|fieldEntry
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|equals
argument_list|(
literal|"bibtextype"
argument_list|)
condition|)
block|{
name|fieldEntry
operator|=
name|bibtex
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// changed section begin - arudert
comment|// resolve field (recognized by leading backslash) or text
name|String
name|field
init|=
name|text
operator|.
name|startsWith
argument_list|(
literal|"\\"
argument_list|)
condition|?
name|getField
argument_list|(
name|bibtex
argument_list|,
name|text
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|,
name|database
argument_list|)
else|:
name|getText
argument_list|(
name|text
argument_list|,
name|database
argument_list|)
decl_stmt|;
comment|// changed section end - arudert
if|if
condition|(
name|field
operator|==
literal|null
condition|)
block|{
name|fieldEntry
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|fieldEntry
operator|=
name|field
expr_stmt|;
block|}
block|}
comment|// System.out.println("OPTION: "+option);
if|if
condition|(
name|option
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|option
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fieldEntry
operator|=
name|option
index|[
name|i
index|]
operator|.
name|format
argument_list|(
name|fieldEntry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fieldEntry
return|;
block|}
default|default:
return|return
literal|""
return|;
block|}
block|}
comment|// added section - begin (arudert)
comment|/** 	 * Do layout for general formatters (no bibtex-entry fields). 	 *  	 * @param database 	 *            Bibtex Database 	 * @return 	 */
DECL|method|doLayout (BibtexDatabase database)
specifier|public
name|String
name|doLayout
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
condition|)
block|{
return|return
name|text
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"bibtext entry fields not allowed in begin or end layout"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
operator|)
operator|||
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
operator|)
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"field and group starts not allowed in begin or end layout"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
operator|)
operator|||
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
operator|)
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"field and group ends not allowed in begin or end layout"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
condition|)
block|{
name|String
name|field
init|=
name|getText
argument_list|(
name|text
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
name|option
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|option
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|field
operator|=
name|option
index|[
name|i
index|]
operator|.
name|format
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|field
return|;
block|}
return|return
literal|""
return|;
block|}
comment|// added section - end (arudert)
DECL|method|getLayoutFormatter (String className, String classPrefix)
specifier|public
specifier|static
name|LayoutFormatter
name|getLayoutFormatter
parameter_list|(
name|String
name|className
parameter_list|,
name|String
name|classPrefix
parameter_list|)
throws|throws
name|Exception
block|{
name|LayoutFormatter
name|f
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|className
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
try|try
block|{
try|try
block|{
name|f
operator|=
operator|(
name|LayoutFormatter
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|classPrefix
operator|+
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex2
parameter_list|)
block|{
name|f
operator|=
operator|(
name|LayoutFormatter
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Formatter not found"
argument_list|)
operator|+
literal|": "
operator|+
name|className
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|className
operator|+
literal|" can not be instantiated."
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|className
operator|+
literal|" can't be accessed."
argument_list|)
throw|;
block|}
block|}
return|return
name|f
return|;
block|}
comment|/** 	 * Return an array of LayoutFormatters found in the given formatterName 	 * string (in order of appearance). 	 *  	 */
DECL|method|getOptionalLayout (String formatterName, String classPrefix)
specifier|public
specifier|static
name|LayoutFormatter
index|[]
name|getOptionalLayout
parameter_list|(
name|String
name|formatterName
parameter_list|,
name|String
name|classPrefix
parameter_list|)
throws|throws
name|Exception
block|{
name|ArrayList
name|formatterStrings
init|=
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
name|formatterName
argument_list|)
decl_stmt|;
name|ArrayList
name|results
init|=
operator|new
name|ArrayList
argument_list|(
name|formatterStrings
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|Map
name|userNameFormatter
init|=
name|NameFormatterTab
operator|.
name|getNameFormatters
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|formatterStrings
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|strings
init|=
operator|(
name|String
index|[]
operator|)
name|formatterStrings
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|className
init|=
name|strings
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
try|try
block|{
name|LayoutFormatter
name|f
init|=
name|getLayoutFormatter
argument_list|(
name|className
argument_list|,
name|classPrefix
argument_list|)
decl_stmt|;
name|results
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|String
name|formatterParameter
init|=
operator|(
name|String
operator|)
name|userNameFormatter
operator|.
name|get
argument_list|(
name|className
argument_list|)
decl_stmt|;
if|if
condition|(
name|formatterParameter
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Formatter not found"
argument_list|)
operator|+
literal|": "
operator|+
name|className
argument_list|)
throw|;
block|}
else|else
block|{
name|NameFormat
name|nf
init|=
operator|new
name|NameFormat
argument_list|()
decl_stmt|;
name|nf
operator|.
name|setParameter
argument_list|(
name|formatterParameter
argument_list|)
expr_stmt|;
name|results
operator|.
name|add
argument_list|(
name|nf
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|LayoutFormatter
index|[]
operator|)
name|results
operator|.
name|toArray
argument_list|(
operator|new
name|LayoutFormatter
index|[]
block|{}
argument_list|)
return|;
block|}
comment|// changed section begin - arudert
comment|/** 	 * Returns a text with references resolved according to an optionally given 	 * database. 	 */
DECL|method|getText (String text, BibtexDatabase database)
specifier|private
name|String
name|getText
parameter_list|(
name|String
name|text
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|String
name|res
init|=
name|text
decl_stmt|;
comment|// changed section end - arudert
if|if
condition|(
operator|(
name|res
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|database
operator|!=
literal|null
operator|)
condition|)
name|res
operator|=
name|database
operator|.
name|resolveForStrings
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
comment|// changed section end - arudert
DECL|method|getField (BibtexEntry bibtex, String field, BibtexDatabase database)
specifier|private
name|String
name|getField
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|,
name|String
name|field
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
comment|// Change: Morten Alver, May 23, 2006. Formatter argument uses this
comment|// method to
comment|// resolve field values. We need this part to resolve \bibtextype
comment|// correctly in
comment|// constructs like \format[ToLowerCase]{\bibtextype}:
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
literal|"bibtextype"
argument_list|)
condition|)
block|{
return|return
name|bibtex
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
comment|// end change Morten Alver
name|String
name|res
init|=
operator|(
name|String
operator|)
name|bibtex
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|res
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|database
operator|!=
literal|null
operator|)
condition|)
name|res
operator|=
name|database
operator|.
name|resolveForStrings
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
block|}
end_class

begin_comment
comment|// /////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|// END OF FILE.
end_comment

begin_comment
comment|// /////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

