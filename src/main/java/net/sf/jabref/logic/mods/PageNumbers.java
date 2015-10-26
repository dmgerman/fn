begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.mods
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|mods
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
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
import|;
end_import

begin_comment
comment|/**  * @author Michael Wrighton  * @author S M Mahbub Murshed  *  */
end_comment

begin_class
DECL|class|PageNumbers
specifier|public
class|class
name|PageNumbers
block|{
DECL|field|freeform
specifier|private
name|String
name|freeform
decl_stmt|;
DECL|field|start
specifier|private
name|int
name|start
decl_stmt|;
DECL|field|end
specifier|private
name|int
name|end
decl_stmt|;
DECL|method|PageNumbers (String s)
specifier|public
name|PageNumbers
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|parsePageNums
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|parsePageNums (String numberString)
specifier|private
name|void
name|parsePageNums
parameter_list|(
name|String
name|numberString
parameter_list|)
block|{
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\s*(\\d+)\\s*-{1,2}\\s*(\\d+)\\s*"
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|numberString
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|matches
argument_list|()
condition|)
block|{
name|start
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|end
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|freeform
operator|=
name|numberString
expr_stmt|;
block|}
block|}
DECL|method|getDOMrepresentation (Document document)
specifier|public
name|Element
name|getDOMrepresentation
parameter_list|(
name|Document
name|document
parameter_list|)
block|{
name|Element
name|result
init|=
name|document
operator|.
name|createElement
argument_list|(
literal|"extent"
argument_list|)
decl_stmt|;
name|result
operator|.
name|setAttribute
argument_list|(
literal|"unit"
argument_list|,
literal|"page"
argument_list|)
expr_stmt|;
if|if
condition|(
name|freeform
operator|!=
literal|null
condition|)
block|{
name|Node
name|textNode
init|=
name|document
operator|.
name|createTextNode
argument_list|(
name|freeform
argument_list|)
decl_stmt|;
name|result
operator|.
name|appendChild
argument_list|(
name|textNode
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Element
name|tmpStart
init|=
name|document
operator|.
name|createElement
argument_list|(
literal|"start"
argument_list|)
decl_stmt|;
name|Element
name|tmpEnd
init|=
name|document
operator|.
name|createElement
argument_list|(
literal|"end"
argument_list|)
decl_stmt|;
name|tmpStart
operator|.
name|appendChild
argument_list|(
name|document
operator|.
name|createTextNode
argument_list|(
literal|""
operator|+
name|this
operator|.
name|start
argument_list|)
argument_list|)
expr_stmt|;
name|tmpEnd
operator|.
name|appendChild
argument_list|(
name|document
operator|.
name|createTextNode
argument_list|(
literal|""
operator|+
name|this
operator|.
name|end
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|appendChild
argument_list|(
name|tmpStart
argument_list|)
expr_stmt|;
name|result
operator|.
name|appendChild
argument_list|(
name|tmpEnd
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|toString (String separator)
specifier|public
name|String
name|toString
parameter_list|(
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|freeform
operator|!=
literal|null
condition|)
block|{
return|return
name|freeform
return|;
block|}
return|return
name|start
operator|+
name|separator
operator|+
name|end
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toString
argument_list|(
literal|"--"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

