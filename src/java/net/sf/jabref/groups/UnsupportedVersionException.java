begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* All programs in this directory and subdirectories are published under the  GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it  under the terms of the GNU General Public License as published by the Free  Software Foundation; either version 2 of the License, or (at your option)  any later version.  This program is distributed in the hope that it will be useful, but WITHOUT  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for  more details.  You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc., 59  Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_class
DECL|class|UnsupportedVersionException
specifier|public
class|class
name|UnsupportedVersionException
extends|extends
name|Exception
block|{
DECL|method|UnsupportedVersionException (String groupType, int version)
specifier|public
name|UnsupportedVersionException
parameter_list|(
name|String
name|groupType
parameter_list|,
name|int
name|version
parameter_list|)
block|{
name|super
argument_list|(
literal|"Unsupported version for "
operator|+
name|groupType
operator|+
literal|": "
operator|+
name|version
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

