begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2006 Jabref-Team  *   * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
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

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|AuthorLastFirstOxfordCommasTest
specifier|public
class|class
name|AuthorLastFirstOxfordCommasTest
block|{
comment|/**      * Test method for {@link net.sf.jabref.export.layout.format.AuthorLastFirstOxfordCommas#format(java.lang.String)}.      */
annotation|@
name|Test
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|AuthorLastFirstOxfordCommas
argument_list|()
decl_stmt|;
comment|// Empty case
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
comment|// Single Names
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Someone, Van Something"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Van Something Someone"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Two names
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"von Neumann, John and Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Three names
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"von Neumann, John, Smith, John, and Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"von Neumann, John, Smith, John, and Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

