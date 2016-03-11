begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.layout.format
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
operator|.
name|format
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
name|layout
operator|.
name|LayoutFormatter
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
name|DOI
import|;
end_import

begin_comment
comment|/**  * Used to fix [ 1588028 ] export HTML table DOI URL.  *  * Will prepend "http://doi.org/" if only DOI and not an URL is given.  */
end_comment

begin_class
DECL|class|DOICheck
specifier|public
class|class
name|DOICheck
implements|implements
name|LayoutFormatter
block|{
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
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
name|result
init|=
name|fieldText
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|result
operator|=
name|result
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
return|return
name|DOI
operator|.
name|build
argument_list|(
name|result
argument_list|)
operator|.
name|map
argument_list|(
name|DOI
operator|::
name|getURLAsASCIIString
argument_list|)
operator|.
name|orElse
argument_list|(
name|result
argument_list|)
return|;
block|}
block|}
end_class

end_unit
