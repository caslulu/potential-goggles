import {useState} from 'react';

function MeuHeader()
{
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  function onToggleMenu(e){
    setIsMenuOpen(!isMenuOpen);
  }
  return (
  <header className="h-screen"> 
    <nav className="flex justify-between items-center w-[92%] mx-auto">
          <div>
            <img className="w-16" src="https://cdn-icons-png.flaticon.com/512/5968/5968204.png" alt="..."/>
          </div>  
            <div className={`md:static absolute md:min-h-fit min-h-[60vh] left-0 md:w-auto w-full flex items-center px-5 ${isMenuOpen ? 'top-[9%]' : 'top-[-100%]'}`}>
              <ul className="flex md:flex-row flex-col md:items-center md:gap-[4vw] gap-8">
                <li>
                  <a className="hover:text-gray-500" href="#">About</a>
                </li>
                <li>
                  <a className="hover:text-gray-500" href="#">Plans</a>
                </li>
                <li>
                  <a className="hover:text-gray-500" href="#">Locations</a>
                </li>
                <li>
                  <a className="hover:text-gray-500" href="#">Developer</a>
                </li>
              </ul>
            </div>
          <div className="flex items-center gap-6">
              <a className="bg-blue-500 text-white rounded-2xl px-5 py-2 hover:-translate-y-0.5 hover:scale-110 hover:shadow-2xs" href="#">Sign In</a>
              <ion-icon name={isMenuOpen ? "close-outline" : "menu-outline"} className="text-3x1 cursor-pointer md:hidden" onClick={onToggleMenu}></ion-icon>
          </div>
    </nav>
  </header>
  );
}


export default MeuHeader
