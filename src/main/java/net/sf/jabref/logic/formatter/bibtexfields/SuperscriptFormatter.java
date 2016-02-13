begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
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
name|bibtexfields
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
name|logic
operator|.
name|formatter
operator|.
name|Formatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_comment
comment|/**  * This class transforms ordinal numbers into LaTex superscripts.  */
end_comment

begin_class
DECL|class|SuperscriptFormatter
specifier|public
class|class
name|SuperscriptFormatter
implements|implements
name|Formatter
block|{
comment|// find possible superscripts on word boundaries
DECL|field|SUPERSCRIPT_DETECT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|SUPERSCRIPT_DETECT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b(\\d+)(st|nd|rd|th)\\b"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
operator||
name|Pattern
operator|.
name|MULTILINE
argument_list|)
decl_stmt|;
DECL|field|SUPERSCRIPT_REPLACE_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|SUPERSCRIPT_REPLACE_PATTERN
init|=
literal|"$1\\\\textsuperscript{$2}"
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Superscripts"
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"SuperscriptFormatter"
return|;
block|}
comment|/**      * Converts ordinal numbers to superscripts, e.g. 1st, 2nd or 3rd.      * Will replace ordinal numbers even if they are semantically wrong, e.g. 21rd      *      *<example>      * 1st Conf. Cloud Computing -> 1\textsuperscript{st} Conf. Cloud Computing      *</example>      */
annotation|@
name|Override
DECL|method|format (String value)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
block|{
comment|// nothing to do
if|if
condition|(
operator|(
name|value
operator|==
literal|null
operator|)
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|value
return|;
block|}
name|Matcher
name|matcher
init|=
name|SUPERSCRIPT_DETECT_PATTERN
operator|.
name|matcher
argument_list|(
name|value
argument_list|)
decl_stmt|;
comment|// replace globally
comment|// adds superscript tag
return|return
name|matcher
operator|.
name|replaceAll
argument_list|(
name|SUPERSCRIPT_REPLACE_PATTERN
argument_list|)
return|;
block|}
block|}
end_class

end_unit

