begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout.format
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
operator|.
name|format
package|;
end_package

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
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Will interpret two consecutive newlines as the start of a new paragraph and thus  * wrap the paragraph in HTML-p-tags.  */
end_comment

begin_class
DECL|class|HTMLParagraphs
specifier|public
class|class
name|HTMLParagraphs
implements|implements
name|LayoutFormatter
block|{
DECL|field|beforeNewLines
specifier|private
specifier|static
name|Pattern
name|beforeNewLines
decl_stmt|;
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|fieldText
operator|=
name|fieldText
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|fieldText
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|fieldText
return|;
block|}
if|if
condition|(
name|HTMLParagraphs
operator|.
name|beforeNewLines
operator|==
literal|null
condition|)
block|{
name|HTMLParagraphs
operator|.
name|beforeNewLines
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(.*?)\\n\\s*\\n"
argument_list|)
expr_stmt|;
block|}
name|Matcher
name|m
init|=
name|HTMLParagraphs
operator|.
name|beforeNewLines
operator|.
name|matcher
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
name|StringBuffer
name|s
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|middle
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|middle
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|s
operator|.
name|append
argument_list|(
literal|"<p>\n"
argument_list|)
expr_stmt|;
name|m
operator|.
name|appendReplacement
argument_list|(
name|s
argument_list|,
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|s
operator|.
name|append
argument_list|(
literal|"\n</p>\n"
argument_list|)
expr_stmt|;
block|}
block|}
name|s
operator|.
name|append
argument_list|(
literal|"<p>\n"
argument_list|)
expr_stmt|;
name|m
operator|.
name|appendTail
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|s
operator|.
name|append
argument_list|(
literal|"\n</p>"
argument_list|)
expr_stmt|;
return|return
name|s
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

