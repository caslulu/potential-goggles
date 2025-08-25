function MeuFooter() {
  return(
    <footer>
        <div className="container mx-auto py-14 px-6">
          <div className="grid md:grid-cols-12 grid-cols-1 gap-7">
            <div className="lg:col-span-4 col-span-12">
              <a href="/">
                <img className="h-12" src="https://res.cloudinary.com/daiyj7fx1/image/upload/v1676395286/Logo/NAV-BROWN_qbsht2.svg" alt=""/>
              </a>
              <p className="mt-6">Lorem ipsun Lorem ipsunLorem ipsunLorem ipsunLorem ipsunLorem ipsunLorem ipsunLorem ipsunLorem ipsun
              Lorem ipsunLorem ipsunLorem ipsunLorem ipsunLorem ipsunLorem ipsun </p>
            </div>  
            <div className="lg:col-span-2 md:col-span-4 col-span-12">
            <h5 className="tracking-wide font-semibold">Company</h5>
              <ul className="list-none mt-6 space-y-2">
                <li>
                  <a href="#" className="hover:text-gray-500 transition-all duration-500 ease-in-out">About us</a>
                </li>
                <li>
                  <a href="#" className="hover:text-gray-500 transition-all duration-500 ease-in-out">Services</a>
                </li>
                <li>
                  <a href="#" className="hover:text-gray-500 transition-all duration-500 ease-in-out">Pricing</a>
                </li>
                <li>
                  <a href="#" className="hover:text-gray-500 transition-all duration-500 ease-in-out">Blog</a>
                </li>
              </ul>
            </div>
            <div className="lg:col-span-3 md:col-span-4 col-span-12">
              <h5 tracking-wide font-semibold>Important Links</h5>
                <ul className="list-none space-y-2 mt-6">
                  <li>
                    <a href="#" className="hover:text-gray-500 transition-all duration-500 ease-in-out">Privacy Policy</a>
                  </li>
                  <li>
                    <a href="#" className="hover:text-gray-500 transition-all duration-500 ease-in-out">Documentation</a>
                  </li>
                </ul>
            </div>
            <div className="lg:col-span-3 md:col-span-4 col-span-12">
              <h5 className="tracking-wide font-semibold">Newsletter</h5>
              <p className="mt-6">Sign up and recieve the latest tips via email</p>
              <form action="">
                <div className="my-3">
                  <label for="email-input">Write your email <span className="text-red-600">*</span></label>
                  <input type="email" id="email-input" 
                  className="mt-3 w-full py-2 px-3 h-10 bg-transparent rounded outline-none border border-gray-500 focus:border-blue-500 focus:ring-0"
                  placeholder="name@example.com"/>
                  <input type="submit" className="mt-3 py-2 px-5 tracking-wide border duration-500 text-base text-center bg-blue-500 hover:bg-blue-400 border-blue-700 hover:border-blue-900 text-black rounded-md w-full"/>
                </div>
              </form>
            </div>
          </div>
        </div>
    </footer>
  );
}

export default MeuFooter;
