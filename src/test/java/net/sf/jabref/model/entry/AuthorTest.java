begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|AuthorTest
specifier|public
class|class
name|AuthorTest
block|{
annotation|@
name|Test
DECL|method|addDotIfAbbreviationAddDot ()
specifier|public
name|void
name|addDotIfAbbreviationAddDot
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"AO"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"AO."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A.O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A.-O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A-O"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addDotIfAbbreviationDoNotAddDot ()
specifier|public
name|void
name|addDotIfAbbreviationDoNotAddDot
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A. O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A.-O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A.-O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A. O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"O. von Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O. von Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A.-O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A.-O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O., Jr."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O., Jr."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, A. O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, A.-O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, A.-O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"MEmre"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"MEmre"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\'{E}}douard"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"{\\'{E}}douard"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J{\\\"o}rg"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"J{\\\"o}rg"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O. and O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O. and O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O. and O. Moore and Moore, O. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O. and O. Moore and Moore, O. O."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

