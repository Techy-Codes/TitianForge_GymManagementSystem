<%-- 
    Document   : RenewSuccess
    Created on : 20-Aug-2023, 1:10:45 pm
    Author     : hevin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Titian Forge Fitness</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="logo.png">
    <style>

.navbar
{
    display: flex;
    align-items: center;
    padding: 20px;
    padding-left: 50px;
    padding-right: 30px;
    padding-top: 50px;
}
nav
{
    flex: 1;
    text-align: right;
}
nav ul 
{
    display: inline-block;
    list-style: none;
}
nav ul li
{
    display: inline-block;
    margin-right: 70px;
}
nav ul li a
{
    text-decoration: none;
    font-size: 20px;
    color: #f31194;
    font-family: sans-serif;
}
nav ul li button
{
    font-size: 20px;
    color: white;
    outline: none;
    border: none;
    background: transparent;
    cursor: pointer;
    font-family: sans-serif;
}
nav ul li button:hover
{
    color: aqua;
}
nav ul li a:hover
{
    color: aqua;
}
a
{
    text-decoration: none;
    color: palevioletred;
    font-size: 28px;
}
.btn {
  display: inline-block;
  margin-top: 1rem;
  padding: .8rem 2.8rem;
  border-radius: 5rem;
  border-top-left-radius: 0;
  border: 0.2rem solid #130f40;
  cursor: pointer;
  background: none;
  color: #f31194;
  font-size: 1.7rem;
  overflow: hidden;
  z-index: 0;
  position: relative;
}

.btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  background: #130f40;
  z-index: -1;
  -webkit-transition: .2s linear;
  transition: .2s linear;
  -webkit-clip-path: circle(0% at 0% 5%);
          clip-path: circle(0% at 0% 5%);
}

.btn:hover::before {
  -webkit-clip-path: circle(100%);
          clip-path: circle(100%);
}

.btn:hover {
  color: #fff;
}

.about {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
      -ms-flex-align: center;
          align-items: center;
  -ms-flex-wrap: wrap;
      flex-wrap: wrap;
  gap: 2rem;
}

.about .image {
  -webkit-box-flex: 1;
      -ms-flex: 1 1 40rem;
          flex: 1 1 40rem;
}

.about .image img {
  width: 50%;
}

.about .content {
  -webkit-box-flex: 1;
      -ms-flex: 1 1 40rem;
          flex: 1 1 40rem;
}

.about .content h3 {
  color: #130f40;
  font-size: 4rem;
}

.about .content h3 span {
  color: green;
}

.about .content p {
  font-size: 1.6rem;
  color: #666;
  padding: 1rem 0;
  line-height: 2;
}
</style>
</head>
<body>
            <div>
                <a href='#'>Titan Forge Fitness</a>
            </div>
          
      
   
<section class="about" id="about">

    <div class="image">
        <img src="images/success.jpeg" alt="">
    </div>
<div class="content">
        <h3>Renewal<span> Successful!!</span></h3><BR>
        <p> 
"Embrace this new chapter with courage and dedication. Your journey starts now, and every step you take brings you closer to your goals. You've got this!"</p>
       <center><button class='btn' onclick="window.location.href='RenewalReceipt';" style="width:auto;">Print</button></center>

    </div>
    <BR><BR>
     
</section>
 
<!-- about section ends -->
</body>
</html>
