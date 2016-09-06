begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|EventHandler
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TableCell
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TableColumn
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|MouseEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|util
operator|.
name|Callback
import|;
end_import

begin_comment
comment|/**  * Constructs a {@link TableCell} based on the value of the cell and a bunch of specified converter methods.  *  * @param<S> view model  * @param<T> cell value  */
end_comment

begin_class
DECL|class|ValueTableCellFactory
specifier|public
class|class
name|ValueTableCellFactory
parameter_list|<
name|S
parameter_list|,
name|T
parameter_list|>
implements|implements
name|Callback
argument_list|<
name|TableColumn
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
argument_list|,
name|TableCell
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
argument_list|>
block|{
DECL|field|toText
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toText
decl_stmt|;
DECL|field|toGraphic
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|Node
argument_list|>
name|toGraphic
decl_stmt|;
DECL|field|toOnMouseClickedEvent
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|EventHandler
argument_list|<
name|?
super|super
name|MouseEvent
argument_list|>
argument_list|>
name|toOnMouseClickedEvent
decl_stmt|;
DECL|method|withText (Callback<T, String> toText)
specifier|public
name|ValueTableCellFactory
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
name|withText
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toText
parameter_list|)
block|{
name|this
operator|.
name|toText
operator|=
name|toText
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withGraphic (Callback<T, Node> toGraphic)
specifier|public
name|ValueTableCellFactory
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
name|withGraphic
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|Node
argument_list|>
name|toGraphic
parameter_list|)
block|{
name|this
operator|.
name|toGraphic
operator|=
name|toGraphic
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withOnMouseClickedEvent ( Callback<T, EventHandler<? super MouseEvent>> toOnMouseClickedEvent)
specifier|public
name|ValueTableCellFactory
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
name|withOnMouseClickedEvent
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|EventHandler
argument_list|<
name|?
super|super
name|MouseEvent
argument_list|>
argument_list|>
name|toOnMouseClickedEvent
parameter_list|)
block|{
name|this
operator|.
name|toOnMouseClickedEvent
operator|=
name|toOnMouseClickedEvent
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
DECL|method|call (TableColumn<S, T> param)
specifier|public
name|TableCell
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
name|call
parameter_list|(
name|TableColumn
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
name|param
parameter_list|)
block|{
return|return
operator|new
name|TableCell
argument_list|<
name|S
argument_list|,
name|T
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|void
name|updateItem
parameter_list|(
name|T
name|item
parameter_list|,
name|boolean
name|empty
parameter_list|)
block|{
name|super
operator|.
name|updateItem
argument_list|(
name|item
argument_list|,
name|empty
argument_list|)
expr_stmt|;
if|if
condition|(
name|empty
operator|||
name|item
operator|==
literal|null
condition|)
block|{
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setGraphic
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setOnMouseClicked
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|toText
operator|!=
literal|null
condition|)
block|{
name|setText
argument_list|(
name|toText
operator|.
name|call
argument_list|(
name|item
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toGraphic
operator|!=
literal|null
condition|)
block|{
name|setGraphic
argument_list|(
name|toGraphic
operator|.
name|call
argument_list|(
name|item
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toOnMouseClickedEvent
operator|!=
literal|null
condition|)
block|{
name|setOnMouseClicked
argument_list|(
name|toOnMouseClickedEvent
operator|.
name|call
argument_list|(
name|item
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|;
block|}
block|}
end_class

end_unit

