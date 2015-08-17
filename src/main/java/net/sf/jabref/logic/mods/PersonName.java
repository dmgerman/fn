begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|logic
operator|.
name|AuthorList
import|;
end_import

begin_comment
comment|/**  * @author Michael Wrighton, S M Mahbub Murshed  *  * S M Mahbub Murshed : added few functions for convenience. May 15, 2007  *  * History  * Dec 16, 2011 - Changed parseName(String) to export authorname with  * 				  more than 3 names correctly  *  */
end_comment

begin_class
DECL|class|PersonName
specifier|public
class|class
name|PersonName
block|{
DECL|field|givenName
specifier|private
name|String
name|givenName
decl_stmt|;
DECL|field|surname
specifier|private
name|String
name|surname
decl_stmt|;
DECL|field|middleName
specifier|private
name|String
name|middleName
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
DECL|method|PersonName (String firstName, String middleName, String lastName)
specifier|public
name|PersonName
parameter_list|(
name|String
name|firstName
parameter_list|,
name|String
name|middleName
parameter_list|,
name|String
name|lastName
parameter_list|)
block|{
name|givenName
operator|=
name|firstName
expr_stmt|;
name|this
operator|.
name|middleName
operator|=
name|middleName
expr_stmt|;
name|surname
operator|=
name|lastName
expr_stmt|;
block|}
DECL|method|parseName (String author)
specifier|private
name|void
name|parseName
parameter_list|(
name|String
name|author
parameter_list|)
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|names
init|=
operator|new
name|Vector
argument_list|<>
argument_list|()
decl_stmt|;
name|String
name|authorMod
init|=
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|author
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//Formating names and replacing escape Char for ',' back to a comma
comment|//            XMLChars xmlChars = new XMLChars();
comment|//            authorMod = xmlChars.format(authorMod).replace("&#44;", ",");
name|int
name|endOfLastName
init|=
name|authorMod
operator|.
name|indexOf
argument_list|(
literal|","
argument_list|)
decl_stmt|;
comment|// Tokenize just the firstName and middleNames as we have the surname
comment|// before the comma.
name|WSITools
operator|.
name|tokenize
argument_list|(
name|names
argument_list|,
name|authorMod
operator|.
name|substring
argument_list|(
name|endOfLastName
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|" \n\r"
argument_list|)
expr_stmt|;
if|if
condition|(
name|endOfLastName
operator|>=
literal|0
condition|)
block|{
name|names
operator|.
name|add
argument_list|(
name|authorMod
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|endOfLastName
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|int
name|amountOfNames
init|=
name|names
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
name|amountOfNames
operator|==
literal|1
condition|)
block|{
name|surname
operator|=
name|names
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|amountOfNames
operator|==
literal|2
condition|)
block|{
name|givenName
operator|=
name|names
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|surname
operator|=
name|names
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
name|names
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|middleName
operator|=
literal|""
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
name|amountOfNames
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|middleName
operator|+=
literal|' '
operator|+
name|names
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|middleName
operator|=
name|middleName
operator|.
name|trim
argument_list|()
expr_stmt|;
name|surname
operator|=
name|names
operator|.
name|get
argument_list|(
name|amountOfNames
operator|-
literal|1
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
block|{
name|result
operator|+=
name|givenName
expr_stmt|;
block|}
if|if
condition|(
name|middleName
operator|!=
literal|null
condition|)
block|{
name|result
operator|+=
literal|' '
operator|+
name|middleName
expr_stmt|;
block|}
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
operator|!
name|givenName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fullName
operator|+=
name|givenName
operator|+
literal|' '
expr_stmt|;
block|}
if|if
condition|(
name|middleName
operator|!=
literal|null
operator|&&
operator|!
name|middleName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fullName
operator|+=
name|middleName
operator|+
literal|' '
expr_stmt|;
block|}
if|if
condition|(
name|surname
operator|!=
literal|null
operator|&&
operator|!
name|surname
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fullName
operator|+=
name|surname
expr_stmt|;
block|}
return|return
name|fullName
operator|.
name|trim
argument_list|()
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
name|surname
return|;
block|}
block|}
end_class

end_unit

