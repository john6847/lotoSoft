

            <!DOCTYPE html>
            <html lang="en"
                  xmlns:th="http://www.thymeleaf.org"
                  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
                  layout:decorator="default">
            <head>
              <meta charset="UTF-8" />
              <title>Two Factor Authorization Demo</title>
            </head>
            <body>
            <div layout:fragment="content" class="container">
              <div class="col-lg-12 alert alert-danger text-center" th:if="${param.error}">There was an error with your login.</div>
              <div class="col-lg-4 offset-lg-4 text-left">
                <form th:action="@{/verify}" method="post">
                  <h1>Verify</h1>
                  <p>A text message has been sent to your mobile device. Please enter the code below:</p>
                  <div class="form-group">
                    <label for="code">Verification Code</label>
                    <input type="text" class="form-control" id="code" name="code" placeholder="4-Digit Code" />
                  </div>
                  <button type="submit" class="btn btn-primary">Verify</button>
                </form>
              </div>
            </div>
            </body>
            </html>