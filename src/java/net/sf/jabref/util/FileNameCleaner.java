begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 Sarel Botha     This class has been copied from http://stackoverflow.com/a/5626340/873282      This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>.  */
end_comment

begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_class
DECL|class|FileNameCleaner
specifier|public
class|class
name|FileNameCleaner
block|{
DECL|field|illegalChars
specifier|final
specifier|static
name|int
index|[]
name|illegalChars
init|=
block|{
literal|34
block|,
literal|60
block|,
literal|62
block|,
literal|124
block|,
literal|0
block|,
literal|1
block|,
literal|2
block|,
literal|3
block|,
literal|4
block|,
literal|5
block|,
literal|6
block|,
literal|7
block|,
literal|8
block|,
literal|9
block|,
literal|10
block|,
literal|11
block|,
literal|12
block|,
literal|13
block|,
literal|14
block|,
literal|15
block|,
literal|16
block|,
literal|17
block|,
literal|18
block|,
literal|19
block|,
literal|20
block|,
literal|21
block|,
literal|22
block|,
literal|23
block|,
literal|24
block|,
literal|25
block|,
literal|26
block|,
literal|27
block|,
literal|28
block|,
literal|29
block|,
literal|30
block|,
literal|31
block|,
literal|58
block|,
literal|42
block|,
literal|63
block|,
literal|92
block|,
literal|47
block|}
decl_stmt|;
static|static
block|{
name|Arrays
operator|.
name|sort
argument_list|(
name|illegalChars
argument_list|)
expr_stmt|;
block|}
DECL|method|cleanFileName (String badFileName)
specifier|public
specifier|static
name|String
name|cleanFileName
parameter_list|(
name|String
name|badFileName
parameter_list|)
block|{
name|StringBuilder
name|cleanName
init|=
operator|new
name|StringBuilder
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
name|badFileName
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|int
name|c
init|=
operator|(
name|int
operator|)
name|badFileName
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|Arrays
operator|.
name|binarySearch
argument_list|(
name|illegalChars
argument_list|,
name|c
argument_list|)
operator|<
literal|0
condition|)
block|{
name|cleanName
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|cleanName
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

