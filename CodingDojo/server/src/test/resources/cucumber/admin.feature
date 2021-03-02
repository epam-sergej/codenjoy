Feature: Admin page
  Admin page main features

Scenario: User cant open admin page but Admin can
  Given Clean all registration data
  Given User registered with name 'Stiven Pupkin', email 'user1@mail.com', password 'password1', city 'Moon', tech skills 'Java', company 'Home', experience '10 years'

  When Login as 'user1@mail.com' 'password1'
  When Try open Admin page
  Then Error page opened with message 'Something wrong with your request. Please save you ticker number and ask site administrator.'

  When Login as 'admin@codenjoyme.com' 'admin'
  When Try open Admin page
  Then Admin page opened with url '/admin?room=first'

  And Close browser