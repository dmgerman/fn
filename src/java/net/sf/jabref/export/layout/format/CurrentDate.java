begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2005 Andreas Rudert   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html */
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
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Inserts the current date (the time a database is being exported).  *   *<p>If a fieldText is given, it must be a valid {@link SimpleDateFormat} pattern.  * If none is given, the format pattern will be<code>yyyy.MM.dd hh:mm:ss z</code></p>  *  * @author andreas_sf at rudert-home dot de  * @version $Revision$  */
end_comment

begin_class
DECL|class|CurrentDate
specifier|public
class|class
name|CurrentDate
implements|implements
name|LayoutFormatter
block|{
DECL|field|defaultFormat
specifier|private
specifier|static
specifier|final
name|String
name|defaultFormat
init|=
literal|"yyyy.MM.dd hh:mm:ss z"
decl_stmt|;
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.export.layout.LayoutFormatter#format(java.lang.String)      */
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|String
name|format
init|=
name|defaultFormat
decl_stmt|;
if|if
condition|(
name|fieldText
operator|!=
literal|null
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|fieldText
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
name|format
operator|=
name|fieldText
expr_stmt|;
block|}
return|return
operator|new
name|SimpleDateFormat
argument_list|(
name|format
argument_list|)
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

