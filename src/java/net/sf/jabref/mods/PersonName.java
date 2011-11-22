begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.mods
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|export
operator|.
name|layout
operator|.
name|WSITools
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
name|AuthorList
import|;
end_import

begin_comment
comment|/**  * @author Michael Wrighton, S M Mahbub Murshed  *  * S M Mahbub Murshed : added few functions for convenience. May 15, 2007  */
end_comment

begin_class
DECL|class|PersonName
specifier|public
class|class
name|PersonName
block|{
DECL|field|givenName
specifier|protected
name|String
name|givenName
init|=
literal|null
decl_stmt|;
DECL|field|surname
specifier|protected
name|String
name|surname
init|=
literal|null
decl_stmt|;
DECL|field|middleName
specifier|protected
name|String
name|middleName
init|=
literal|null
decl_stmt|;
DECL|method|PersonName ()
specifier|public
name|PersonName
parameter_list|()
block|{     }
DECL|method|PersonName (String name)
specifier|public
name|PersonName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|parseName
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|PersonName (String firstName, String _middleName, String lastName)
specifier|public
name|PersonName
parameter_list|(
name|String
name|firstName
parameter_list|,
name|String
name|_middleName
parameter_list|,
name|String
name|lastName
parameter_list|)
block|{
name|givenName
operator|=
name|firstName
expr_stmt|;
name|middleName
operator|=
name|_middleName
expr_stmt|;
name|surname
operator|=
name|lastName
expr_stmt|;
block|}
DECL|method|parseName (String author)
specifier|protected
name|void
name|parseName
parameter_list|(
name|String
name|author
parameter_list|)
block|{
comment|// TODO: replace special characters
name|Vector
argument_list|<
name|String
argument_list|>
name|v
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|authorMod
init|=
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
name|author
argument_list|)
decl_stmt|;
name|WSITools
operator|.
name|tokenize
argument_list|(
name|v
argument_list|,
name|authorMod
argument_list|,
literal|" \n\r"
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
name|surname
operator|=
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|v
operator|.
name|size
argument_list|()
operator|==
literal|2
condition|)
block|{
name|givenName
operator|=
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|surname
operator|=
name|v
operator|.
name|get
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|givenName
operator|=
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|middleName
operator|=
name|v
operator|.
name|get
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|surname
operator|=
name|v
operator|.
name|get
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getGivenNames ()
specifier|public
name|String
name|getGivenNames
parameter_list|()
block|{
name|String
name|result
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|givenName
operator|!=
literal|null
condition|)
name|result
operator|+=
name|givenName
expr_stmt|;
if|if
condition|(
name|middleName
operator|!=
literal|null
condition|)
name|result
operator|+=
literal|" "
operator|+
name|middleName
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|getSurname ()
specifier|public
name|String
name|getSurname
parameter_list|()
block|{
return|return
name|surname
return|;
block|}
DECL|method|setSurname (String lastName)
specifier|public
name|void
name|setSurname
parameter_list|(
name|String
name|lastName
parameter_list|)
block|{
name|surname
operator|=
name|lastName
expr_stmt|;
block|}
DECL|method|getFirstname ()
specifier|public
name|String
name|getFirstname
parameter_list|()
block|{
return|return
name|givenName
return|;
block|}
DECL|method|setFirstname (String firstName)
specifier|public
name|void
name|setFirstname
parameter_list|(
name|String
name|firstName
parameter_list|)
block|{
name|givenName
operator|=
name|firstName
expr_stmt|;
block|}
DECL|method|getMiddlename ()
specifier|public
name|String
name|getMiddlename
parameter_list|()
block|{
return|return
name|middleName
return|;
block|}
DECL|method|setMiddlename (String _middleName)
specifier|public
name|void
name|setMiddlename
parameter_list|(
name|String
name|_middleName
parameter_list|)
block|{
name|middleName
operator|=
name|_middleName
expr_stmt|;
block|}
DECL|method|getFullname ()
specifier|public
name|String
name|getFullname
parameter_list|()
block|{
name|String
name|fullName
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|givenName
operator|!=
literal|null
operator|&&
name|givenName
operator|!=
literal|""
condition|)
name|fullName
operator|+=
name|givenName
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|middleName
operator|!=
literal|null
operator|&&
name|middleName
operator|!=
literal|""
condition|)
name|fullName
operator|+=
name|middleName
operator|+
literal|" "
expr_stmt|;
if|if
condition|(
name|surname
operator|!=
literal|null
operator|&&
name|surname
operator|!=
literal|""
condition|)
name|fullName
operator|+=
name|surname
expr_stmt|;
return|return
name|fullName
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|surname
return|;
block|}
block|}
end_class

end_unit

