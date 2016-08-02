begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

begin_class
DECL|class|FetcherException
specifier|public
class|class
name|FetcherException
extends|extends
name|Exception
block|{
DECL|method|FetcherException (String errorMessage, Exception cause)
specifier|public
name|FetcherException
parameter_list|(
name|String
name|errorMessage
parameter_list|,
name|Exception
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|errorMessage
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
DECL|method|FetcherException (String errorMessage)
specifier|public
name|FetcherException
parameter_list|(
name|String
name|errorMessage
parameter_list|)
block|{
name|super
argument_list|(
name|errorMessage
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
