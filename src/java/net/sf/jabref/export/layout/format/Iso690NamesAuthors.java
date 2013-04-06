begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * iso690NamesAuthors.java  *  * Creado el 02 de agosto de 2009, 16:07  *  * Copyright 2009, Laura HernÃ¡ndez GÃ³mez  *  * This file is part of Plugin ISO 690.  *  *  Plugin ISO 690 is free software: you can redistribute it and/or modify  *  it under the terms of the GNU General Public License as published by  *  the Free Software Foundation, either version 3 of the License, or  *  (at your option) any later version.  *  *  Plugin ISO 690 is distributed in the hope that it will be useful,  *  but WITHOUT ANY WARRANTY; without even the implied warranty of  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  *  GNU General Public License for more details.  *  *  You should have received a copy of the GNU General Public License  *  along with Plugin ISO 690.  If not, see<http://www.gnu.org/licenses/>.  */
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
name|*
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
name|*
import|;
end_import

begin_comment
comment|/**  *  * @author Usuario  */
end_comment

begin_class
DECL|class|Iso690NamesAuthors
specifier|public
class|class
name|Iso690NamesAuthors
implements|implements
name|LayoutFormatter
block|{
comment|/** Creates a new instance of iso690NamesAuthors */
DECL|method|Iso690NamesAuthors ()
specifier|public
name|Iso690NamesAuthors
parameter_list|()
block|{     }
DECL|method|format (String s)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
operator|||
name|s
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
return|return
literal|""
return|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
index|[]
name|authors
init|=
name|s
operator|.
name|split
argument_list|(
literal|"and"
argument_list|)
decl_stmt|;
comment|//parte el string en los distintos autores
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
comment|//parte el string author en varios campos, segÃºn el separador ","
name|String
index|[]
name|author
init|=
name|authors
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
comment|// No separa apellidos y nombre con coma (,)
if|if
condition|(
name|author
operator|.
name|length
operator|<
literal|2
condition|)
block|{
comment|// Caso 1: Nombre Apellidos
comment|//parte el string author en varios campos, segÃºn el separador " "
name|author
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
comment|//declaramos y damos un valor para evitar problemas
name|String
name|name
init|=
literal|""
decl_stmt|,
name|surname
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|author
operator|.
name|length
operator|==
literal|1
condition|)
block|{
comment|// Caso 1.0: SÃ³lo un campo
name|sb
operator|.
name|append
argument_list|(
name|author
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|author
operator|.
name|length
operator|==
literal|2
condition|)
block|{
comment|// Caso 1.1: Nombre Apellido
comment|//primer campo Nombre
name|name
operator|=
name|author
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|//Segundo campo Apellido
name|surname
operator|=
name|author
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
comment|//aÃ±adimos los campos modificados al string final
name|sb
operator|.
name|append
argument_list|(
name|surname
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|author
operator|.
name|length
operator|==
literal|3
condition|)
block|{
comment|// Caso 1.2: Nombre Apellido1 Apellido2
comment|//primer campo Nombre
name|name
operator|=
name|author
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|//Segundo y tercer campo Apellido1 Apellido2
name|surname
operator|=
name|author
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
operator|+
literal|" "
operator|+
name|author
index|[
literal|2
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
comment|//aÃ±adimos los campos modificados al string final
name|sb
operator|.
name|append
argument_list|(
name|surname
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|author
operator|.
name|length
operator|==
literal|4
condition|)
block|{
comment|// Caso 1.3: Nombre SegundoNombre Apellido1 Apellido2
comment|//primer y segundo campo Nombre SegundoNombre
name|name
operator|=
name|author
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
operator|+
literal|" "
operator|+
name|author
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|//tercer y cuarto campo Apellido1 Apellido2
name|surname
operator|=
name|author
index|[
literal|2
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
operator|+
literal|" "
operator|+
name|author
index|[
literal|3
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
comment|//aÃ±adimos los campos modificados al string final
name|sb
operator|.
name|append
argument_list|(
name|surname
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Caso 2: Apellidos, Nombre
comment|// Campo 1 apellidos, lo pasamos a mayusculas
name|String
name|surname
init|=
name|author
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
decl_stmt|;
comment|// campo 2 nombre
name|String
name|name
init|=
name|author
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|//aÃ±adimos los campos modificados al string final
name|sb
operator|.
name|append
argument_list|(
name|surname
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|<
name|authors
operator|.
name|length
operator|-
literal|2
condition|)
block|{
comment|//si hay mas de 2 autores, lo separamos por ", "
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|i
operator|==
name|authors
operator|.
name|length
operator|-
literal|2
condition|)
block|{
comment|// si hay 2 autores, lo separamos por " y "
name|sb
operator|.
name|append
argument_list|(
literal|" y "
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
comment|//retorna el string creado de autores.
block|}
block|}
end_class

end_unit

