begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
comment|/**  * Every Listener that wants to receive events from a search needs to  * implement this interface  *  * @author Ben  *  */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|SearchQueryHighlightListener
specifier|public
interface|interface
name|SearchQueryHighlightListener
block|{
comment|/**      * Pattern with which one can determine what to highlight      *      * @param words null if nothing is searched for      */
DECL|method|highlightPattern (Optional<Pattern> highlightPattern)
name|void
name|highlightPattern
parameter_list|(
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|highlightPattern
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

