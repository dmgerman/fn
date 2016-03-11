begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
package|;
end_package

begin_comment
comment|/**  * The LayoutFormatter is used for a Filter design-pattern.  *   * Implementing classes have to accept a String and returned a formatted version of it.  *   * Example:  *   *   "John von Neumann" => "von Neumann, John"  *  * @version 1.2 - Documentation CO  */
end_comment

begin_interface
DECL|interface|LayoutFormatter
specifier|public
interface|interface
name|LayoutFormatter
block|{
comment|/**      * Failure Mode:      *<p>      * Formatters should be robust in the sense that they always return some      * relevant string.      *<p>      * If the formatter can detect an invalid input it should return the      * original string otherwise it may simply return a wrong output.      *       * @param fieldText      *            The text to layout.      * @return The layouted text.      */
DECL|method|format (String fieldText)
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
